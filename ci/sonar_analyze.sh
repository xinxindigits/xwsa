#!/bin/bash

mvn --batch-mode verify sonar:sonar \
    -Dmaven.skip.test=true \
    -Dsonar.host.url=http://10.0.50.134:9000 \
    -Dsonar.login=zhouyang \
    -Dsonar.password=zhouyang \
    -Dsonar.issuesReport.html.enable=true \
    -Dsonar.analysis.mode=publish \
    -Dsonar.preview.excludePlugins=issueassign,scmstats

if [ $? -eq 0 ]; then
    echo "sonarqube code-analyze over."
fi
