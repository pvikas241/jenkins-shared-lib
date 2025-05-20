def call() {
    node {
        echo "Starting pipeline execution..."
        
        stage('Hello World') {
            script {
                try {
                    echo "Executing Hello World..."
                    helloworld() // Call the shared library function
                    echo "Hello World completed successfully."
                } catch (Exception e) {
                    echo "Hello World stage failed: ${e.getMessage()}"
                    env.FAILED_STAGE = 'HelloWorld'

                    def userDecision = input(
                        message: "Stage '${env.FAILED_STAGE}' failed. Do you want to retry or cancel?",
                        parameters: [
                            [$class: 'ChoiceParameterDefinition', name: 'Decision', choices: 'Retry\nCancel', description: 'Select an option']
                        ]
                    )

                    if (userDecision == 'Retry') {
                        echo "Retrying '${env.FAILED_STAGE}'..."
                        call('HelloWorld')  // Retry ONLY HelloWorld stage
                    } else {
                        echo "Skipping Hello World stage."
                    }
                }
            }
        }

        stage('Hi World') {
            script {
                try {
                    echo "Executing Hi World..."
                    hiworld() // Call the shared library function
                    echo "Hi World completed successfully."
                } catch (Exception e) {
                    echo "Hi World stage failed: ${e.getMessage()}"
                    env.FAILED_STAGE = 'HiWorld'

                    def userDecision = input(
                        message: "Stage '${env.FAILED_STAGE}' failed. Do you want to retry or cancel?",
                        parameters: [
                            [$class: 'ChoiceParameterDefinition', name: 'Decision', choices: 'Retry\nCancel', description: 'Select an option']
                        ]
                    )

                    if (userDecision == 'Retry') {
                        echo "Retrying '${env.FAILED_STAGE}'..."
                        call('HiWorld')  // Retry ONLY HiWorld stage
                    } else {
                        echo "Skipping Hi World stage."
                    }
                }
            }
        }

        echo "Pipeline execution completed."
    }
}
