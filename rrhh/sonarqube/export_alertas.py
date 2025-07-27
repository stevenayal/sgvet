import requests
import csv
import base64

SONAR_URL = 'http://localhost:9000'
PROJECT_KEY = 'SgVet-RRHH'
AUTH_TOKEN = 'sqa_b9088b45704c498913019dab02ec74da6b19b2c2'

API_ISSUES = f"{SONAR_URL}/api/issues/search"

params = {
    'componentKeys': PROJECT_KEY,
    'ps': 500,
    'p': 1
}

headers = {}
if AUTH_TOKEN:
    auth = base64.b64encode(f"{AUTH_TOKEN}:".encode()).decode()
    headers['Authorization'] = f"Basic {auth}"

issues_list = []

while True:
    resp = requests.get(API_ISSUES, params=params, headers=headers)
    resp.raise_for_status()
    data = resp.json()
    issues = data.get('issues', [])
    if not issues:
        break
    for issue in issues:
        issues_list.append({
            'key': issue.get('key'),
            'file': issue.get('component'),
            'line': issue.get('line'),
            'severity': issue.get('severity'),
            'type': issue.get('type'),
            'message': issue.get('message')
        })
    if params['p'] * params['ps'] >= data.get('total', 0):
        break
    params['p'] += 1

with open('sonar_issues_rrhh.csv', 'w', newline='', encoding='utf-8') as f:
    writer = csv.DictWriter(f, fieldnames=['key', 'file', 'line', 'severity', 'type', 'message'])
    writer.writeheader()
    writer.writerows(issues_list)

print(f"Exportadas {len(issues_list)} observaciones a sonar_issues_rrhh.csv")

with open('sonar_issues_rrhh.txt', 'w', encoding='utf-8') as f:
    for i, issue in enumerate(issues_list, 1):
        f.write(f"{i}. [{issue['file']} L{issue['line']}] {issue['severity']} - {issue['type']}: {issue['message']}\n")

print("También se generó sonar_issues_rrhh.txt solo con los mensajes.")
