import requests
import csv

SONAR_URL = "http://localhost:9000"
PROJECT_KEY = "SgVet-RRHH"
AUTH_TOKEN = 'sqp_f34b7d11ab087841800ba38e7f5621764480880c'

auth = (AUTH_TOKEN, '')

# 1. Exportar issues normales
issues_url = f"{SONAR_URL}/api/issues/search?componentKeys={PROJECT_KEY}&ps=500"
resp = requests.get(issues_url, auth=auth)
data = resp.json()

with open('sonar_issues_rrhh.csv', 'w', newline='', encoding='utf-8') as f:
    writer = csv.writer(f)
    writer.writerow(['Key', 'Type', 'Severity', 'Message', 'File', 'Line'])
    for issue in data.get('issues', []):
        writer.writerow([
            issue.get('key'),
            issue.get('type'),
            issue.get('severity'),
            issue.get('message'),
            issue.get('component'),
            issue.get('line', '')
        ])

print(f"Exportados {len(data.get('issues', []))} issues a sonar_issues_rrhh.csv")

# 2. Exportar Quality Gate/Alertas de calidad
qg_url = f"{SONAR_URL}/api/qualitygates/project_status?projectKey={PROJECT_KEY}"
qg = requests.get(qg_url, auth=auth).json()

with open('sonar_qualitygate_rrhh.csv', 'w', newline='', encoding='utf-8') as f:
    writer = csv.writer(f)
    writer.writerow(['Status', 'Metric', 'Actual', 'Expected', 'Level'])
    status = qg.get('projectStatus', {})
    for c in status.get('conditions', []):
        writer.writerow([
            status.get('status', ''),
            c.get('metricKey', ''),
            c.get('actualValue', ''),
            c.get('errorThreshold', ''),
            c.get('level', '')
        ])

print(f"Exportadas alertas de calidad a sonar_qualitygate_rrhh.csv")
