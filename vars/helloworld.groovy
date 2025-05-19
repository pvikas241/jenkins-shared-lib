def call() {
    pipeline {
        agent any

        stages {
            stage('Stage 1 - Hello') {
                steps {
                    runWithApproval('Hello') {
                        echo '[Stage 1] Saying Hello...'
  
                            bat 'exit /b 1'

                    }
                }
            }

            stage('Stage 2 - World') {
                steps {
                    runWithApproval('World') {
                        echo '[Stage 2] Saying World...'
                        // Simulate success
                        echo 'Done!'
                    }
                }
            }
        }
    }
}

// Reusable logic for retry/abort flow
def runWithApproval(String stageName, Closure body) {
    def keepGoing = true

    while (keepGoing) {
        try {
            echo "[${stageName}] Executing stage logic..."
            body()
            keepGoing = false
        } catch (err) {
            echo "[${stageName}] Failed: ${err}"

            def choice = input(
                id: "${stageName}-approval",
                message: "Stage '${stageName}' failed. Retry or Abort?",
                parameters: [
                    [$class: 'ChoiceParameterDefinition', choices: "Retry\nAbort", name: 'Action']
                ]
            )

            if (choice == 'Retry') {
                echo "[${stageName}] Retrying..."
            } else {
                error "[${stageName}] Aborted by user."
            }
        }
    }
}
