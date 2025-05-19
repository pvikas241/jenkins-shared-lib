def call() {
    def keepGoing = true

    while (keepGoing) {
        try {
            echo '[helloWorld] Running Hello World step...'

            // Simulate a command that might fail
            // Replace this with real logic in your case
            sh 'exit 1'  // simulate failure for testing

            echo '[helloWorld] Step completed successfully.'
            keepGoing = false
        } catch (err) {
            echo "[helloWorld] Step failed: ${err}"

            def choice = input(
                id: "helloWorld-approval",
                message: "Step 'helloWorld' failed. Do you want to Retry or Abort?",
                parameters: [
                    [$class: 'ChoiceParameterDefinition', choices: "Retry\nAbort", name: 'Action']
                ]
            )

            if (choice == 'Retry') {
                echo '[helloWorld] Retrying...'
            } else {
                error "[helloWorld] Aborted by user."
            }
        }
    }
}
