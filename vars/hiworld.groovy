def call() {
        script {
            echo "Before error..."
            error("Intentional failure for testing")
            currentBuild.result = 'FAILURE'  // Explicitly mark pipeline as failed
        }
    }
}
