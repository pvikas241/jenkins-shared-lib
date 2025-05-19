def call(String stageToRun) {
    node {
        try {
            if (stageToRun == 'HelloWorld' || stageToRun == 'None') {
                stage('Hello World') {
                    script {
                        echo "Hello World from Shared Library!"
                    }
                }
            }

            if (stageToRun == 'HiWorld' || stageToRun == 'None') {
                stage('Hi World') {
                    script {
                        echo "Hi World from Shared Library!"
                    }
                }
            }
        } catch (Exception e) {
            echo "Stage ${stageToRun} failed: ${e.getMessage()}"
            env.FAILED_STAGE = stageToRun

            // Prompt user for retry or cancel
            def userDecision = input(
                message: "Stage ${env.FAILED_STAGE} failed. Do you want to retry or cancel?",
                parameters: [
                    [$class: 'ChoiceParameterDefinition', name: 'Decision', choices: 'Retry\nCancel', description: 'Select an option']
                ]
            )

            if (userDecision == 'Retry') {
                echo "Retrying ${env.FAILED_STAGE}..."
                call(env.FAILED_STAGE)  // Retry the failed stage
            } else {
                echo "Pipeline aborted by user."
                error("User chose to cancel execution.")
            }
        }
    }
}
