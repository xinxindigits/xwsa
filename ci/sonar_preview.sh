#!/bin/bash

mvn --batch-mode verify sonar:sonar \
    -Dmaven.skip.test=true \
    -Dsonar.host.url=http://10.0.50.134:9000 \
    -Dsonar.login=zhouyang \
    -Dsonar.password=zhouyang \
    -Dsonar.issuesReport.html.enable=true \
    -Dsonar.analysis.mode=preview \
    -Dsonar.gitlab.project_id=$CI_PROJECT_ID \
    -Dsonar.gitlab.commit_sha=$CI_COMMIT_SHA \
    -Dsonar.gitlab.ref_name=$CI_COMMIT_REF_NAME

if [ $? -eq 0 ]; then
    echo "sonarqube code-analyze-preview over."
fi
