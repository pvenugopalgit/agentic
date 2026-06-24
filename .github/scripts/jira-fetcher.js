#!/usr/bin/env node
// Simple Jira fetcher and prompt builder
// Usage: node jira-fetcher.js --issue PROJ-123 --url https://your-jira --user EMAIL --token API_TOKEN
// Or: node jira-fetcher.js --config config.json

const fs = require('fs');
const path = require('path');

function parseArgs() {
  const args = {};
  const argv = process.argv.slice(2);
  for (let i = 0; i < argv.length; i++) {
    const a = argv[i];
    if (a.startsWith('--')) {
      const key = a.slice(2);
      const next = argv[i + 1];
      if (next && !next.startsWith('--')) {
        args[key] = next;
        i++;
      } else {
        args[key] = true;
      }
    }
  }
  return args;
}

function loadConfig(configPath) {
  try {
    const fullPath = path.resolve(configPath);
    const configFile = fs.readFileSync(fullPath, 'utf8');
    const config = JSON.parse(configFile);
    console.log(`✓ Config loaded from: ${fullPath}`);
      // Sanitize common string values (strip accidental surrounding quotes)
      if (config && config.jira) {
        for (const k of ['url', 'user', 'token', 'bearer', 'issue']) {
          if (typeof config.jira[k] === 'string') {
            config.jira[k] = config.jira[k].trim();
            if (config.jira[k].startsWith('"') && config.jira[k].endsWith('"')) {
              config.jira[k] = config.jira[k].slice(1, -1);
            }
          }
        }
      }
    return config;
  } catch (error) {
    console.error(`✗ Failed to load config from ${configPath}:`, error.message);
    process.exit(1);
  }
}

function mergeConfig(configData, cliArgs) {
  // Start with config file values
  const merged = { ...configData };
  
  // Override with CLI arguments (CLI takes precedence)
  if (cliArgs.issue) merged.issue = cliArgs.issue;
  if (cliArgs.i) merged.issue = cliArgs.i;
  if (cliArgs.url) merged.url = cliArgs.url;
  if (cliArgs.u) merged.url = cliArgs.u;
  if (cliArgs.user) merged.user = cliArgs.user;
  if (cliArgs.token) merged.token = cliArgs.token;
  if (cliArgs.bearer) merged.bearer = cliArgs.bearer;
  if (cliArgs.out) merged.out = cliArgs.out;

  // Handle nested jira config object
  if (configData.jira) {
    merged.issue = merged.issue || configData.jira.issue;
    merged.url = merged.url || configData.jira.url;
    merged.user = merged.user || configData.jira.user;
    merged.token = merged.token || configData.jira.token;
    merged.bearer = merged.bearer || configData.jira.bearer;
  }

  return merged;
}

function safeText(value) {
  if (typeof value === 'string') return value.trim();
  if (Array.isArray(value)) return value.filter(Boolean).join(', ');
  if (value && typeof value === 'object') {
    if ('name' in value) return String(value.name).trim();
    if ('displayName' in value) return String(value.displayName).trim();
  }
  return '';
}

function extractAcceptanceCriteria(issue) {
  const fields = issue.fields || {};
  const knownCriteriaFields = [
    'customfield_10016',
    'customfield_10014',
    'customfield_10600',
    'customfield_12345',
  ];

  for (const field of knownCriteriaFields) {
    const value = fields[field];
    if (typeof value === 'string' && value.trim()) return value.trim();
    if (Array.isArray(value) && value.length) return value.filter(Boolean).join('\n').trim();
  }

  // Also support Jira Cloud's Atlassian Document Format (ADF) for description
  if (fields.description) {
    const descText = (typeof fields.description === 'string')
      ? fields.description
      : extractDescription({ fields });
    const criteriaMatch = descText && descText.match(/acceptance criteria[:\s]*([\s\S]+)/i);
    if (criteriaMatch) return criteriaMatch[1].trim();
  }

  return '';
}

function renderComments(issue) {
  const comments = (issue.fields && issue.fields.comment && issue.fields.comment.comments) || [];
  if (!comments.length) return 'No comments were provided.';
  return comments.map(c => {
    const author = safeText((c.author && c.author.displayName) || c.author || '');
    const body = (c.body || '').replace(/\r\n|\r/g, '\n').trim();
    const created = c.created ? ` (${c.created.slice(0,10)})` : '';
    return `- ${author}${created}: ${body}`;
  }).join('\n');
}

function renderAttachments(issue) {
  const attachments = (issue.fields && issue.fields.attachment) || [];
  if (!attachments.length) return 'No attachments were provided.';
  return attachments.map(a => {
    const fileSize = a.size ? `${a.size} bytes` : 'unknown size';
    return `- ${a.filename} (${a.mimeType || 'unknown type'}, ${fileSize})`;
  }).join('\n');
}

function buildJiraFeaturePrompt(issue) {
  const summary = safeText(issue.fields && issue.fields.summary) || `JIRA Story ${issue.key}`;
  const description = extractDescription(issue) || 'No description available.';
  const acceptanceCriteria = extractAcceptanceCriteria(issue) || 'No explicit acceptance criteria found.';
  const labels = safeText(issue.fields && issue.fields.labels);
  const priority = safeText(issue.fields && issue.fields.priority);
  const issueType = safeText(issue.fields && issue.fields.issuetype);
  const reporter = safeText(issue.fields && issue.fields.reporter);
  const assignee = safeText(issue.fields && issue.fields.assignee);
  const components = safeText(issue.fields && issue.fields.components);
  const fixVersions = safeText(issue.fields && issue.fields.fixVersions);

  const comments = renderComments(issue);
  const attachments = renderAttachments(issue);

  const lines = [
    'You are an AI assistant that converts Jira story details into a complete Cucumber feature file.',
    'Return only the finished `.feature` text in valid Gherkin, with no surrounding markdown or JSON.',
    '',
    'Input Jira story data:',
    `- Issue key: ${issue.key}`,
    `- Issue type: ${issueType}`,
    `- Summary: ${summary}`,
    `- Priority: ${priority}`,
    `- Labels: ${labels}`,
    `- Reporter: ${reporter}`,
    `- Assignee: ${assignee}`,
    `- Components: ${components}`,
    `- Fix versions: ${fixVersions}`,
    '',
    'Description:',
    description,
    '',
    'Acceptance criteria:',
    acceptanceCriteria,
    '',
    'Comments:',
    comments,
    '',
    'Attachments:',
    attachments,
    '',
    'Guidelines:',
    '1. Create a `Feature:` title based on the summary and issue type.',
    '2. Add a short feature description using the Jira description, acceptance criteria, and any useful fields.',
    '3. Build one or more well-structured `Scenario:` or `Scenario Outline:` sections that reflect the story intent.',
    '4. Use acceptance criteria as explicit step logic where possible, and keep steps readable for non-technical stakeholders.',
    '5. If attachments contain relevant examples or test data, mention them in a scenario step as a note or comment.',
    '6. Preserve important context from comments when it changes expected behavior or clarifies requirements.',
    '7. If acceptance criteria are missing, infer the most likely behavior from the description and comments, but do not invent unsupported features.',
    '8. Do not include Jira field names, API details, or internal metadata in the feature file.',
    '9. Do not add any disclaimer text like "This feature is generated" or "Example only".',
    '',
    'Generate the final Gherkin content now.'
  ];
  return lines.join('\n');
}

function extractDescription(issue) {
  const fields = issue.fields || {};
  const desc = fields.description;
  if (!desc) return '';
  if (typeof desc === 'string') return desc.trim();

  // desc is likely an Atlassian Document Format (ADF) object. Walk it and collect text.
  function adfToText(node) {
    if (!node) return '';
    if (typeof node === 'string') return node;
    if (Array.isArray(node)) return node.map(adfToText).join('');
    if (node.type === 'text' && typeof node.text === 'string') return node.text;

    const children = (node.content || []).map(adfToText).join('');
    switch (node.type) {
      case 'paragraph':
      case 'heading':
        return children + '\n\n';
      case 'listItem':
        return '- ' + children + '\n';
      case 'bulletList':
      case 'orderedList':
        return children + '\n';
      case 'codeBlock':
        return (node.text || children) + '\n\n';
      default:
        return children;
    }
  }

  try {
    const text = adfToText(desc).replace(/\n{3,}/g, '\n\n').trim();
    return text;
  } catch (e) {
    return '';
  }
}

async function fetchIssue(issueKey, baseUrl, auth) {
  const url = `${baseUrl.replace(/\/$/, '')}/rest/api/3/issue/${encodeURIComponent(issueKey)}?fields=summary,description,labels,priority,reporter,assignee,issuetype,components,fixVersions,comment,attachment`;
  const headers = { 'Accept': 'application/json' };
  if (auth.bearer) headers['Authorization'] = `Bearer ${auth.bearer}`;
  else if (auth.user && auth.token) {
    const basic = Buffer.from(`${auth.user}:${auth.token}`, 'utf-8').toString('base64');
    headers['Authorization'] = `Basic ${basic}`;
  } else {
    throw new Error('Authentication required: provide --user and --token, or --bearer');
  }
  const res = await fetch(url, { headers });
  if (!res.ok) {
    const body = await res.text();
    throw new Error(`Failed to fetch issue ${issueKey}: ${res.status} ${res.statusText} - ${body}`);
  }
  return res.json();
}

async function main() {
  const cliArgs = parseArgs();
  let config = {};

  // Load config from file if --config is provided
  if (cliArgs.config) {
    config = loadConfig(cliArgs.config);
  }

  // Merge config file with CLI arguments (CLI takes precedence)
  const merged = mergeConfig(config, cliArgs);

  let issue = merged.issue;
  const baseUrl = merged.url;
  const user = merged.user;
  const token = merged.token;
  const bearer = merged.bearer;
  const out = merged.out || 'prompt';

  // If issue key wasn't provided via CLI or config, prompt the user at runtime.
  if (!issue) {
    const rl = require('readline').createInterface({ input: process.stdin, output: process.stdout });
    issue = await new Promise(resolve => {
      rl.question('Enter Jira issue key (e.g., PROJ-123): ', answer => {
        rl.close();
        resolve((answer || '').trim());
      });
    });
  }

  if (!issue || !baseUrl) {
    console.error('Usage: node jira-fetcher.js --issue PROJ-123 --url https://your-jira --user EMAIL --token API_TOKEN');
    console.error('Or:    node jira-fetcher.js --config config.json');
    process.exit(1);
  }

  try {
    const jiraIssue = await fetchIssue(issue, baseUrl, { user, token, bearer });
    if (out === 'json') {
      console.log(JSON.stringify(jiraIssue, null, 2));
      return;
    }
    const prompt = buildJiraFeaturePrompt(jiraIssue);
    console.log(prompt);
  } catch (err) {
      console.error('Error:', err && err.message ? err.message : err);
      // Avoid forcing an immediate exit from async handlers (can trigger libuv assertions on Windows).
      // Instead set a non-zero exit code and return so Node can shut down gracefully.
      process.exitCode = 2;
      return;
  }
}

// Ensure fetch exists (Node 18+)
if (typeof fetch === 'undefined') {
  global.fetch = (...args) => import('node-fetch').then(m => m.default(...args));
}

main();
