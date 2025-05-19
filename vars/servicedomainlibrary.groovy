def call(String stageToRun) {
    node {
        try {
            echo "Starting ${stageToRun} stage..."
            
            if (stageToRun == 'HelloWorld' || stageToRun == 'None') {
                helloworld() // Call the HelloWorld library function
            }

            if (stageToRun == 'HiWorld' || stageToRun == 'None') {
                hiworld() // Call the HiWorld library function
            }

            echo "Completed ${stageToRun} stage."
        } catch (Exception e) {
            echo "Stage ${stageToRun} failed: ${e.getMessage()}"
            env.FAILED_STAGE = stageToRun

            // Confirm failure handling block is reached
            echo "Entering failure handling logic for ${env.FAILED_STAGE}..."

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
