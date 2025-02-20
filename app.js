// Firebase configuration
const firebaseConfig = {
    apiKey: "AIzaSyAwFaH2feXhxd3ibWiT8o9mB40jpNgkEFU",
    authDomain: "numerosity-583f5.firebaseapp.com",
    projectId: "numerosity-583f5",
    storageBucket: "numerosity-583f5.firebasestorage.app",
    messagingSenderId: "120661100505",
    appId: "1:120661100505:web:c7c7c9311aef205af77bc8",
    measurementId: "G-PYMKPQHKGN"
};

// Initialize Firebase
firebase.initializeApp(firebaseConfig);
const db = firebase.firestore();

document.addEventListener('DOMContentLoaded', () => {
    const questionContainer = document.getElementById('question-container');
    const resultContainer = document.getElementById('result-container');
    const questionElement = document.getElementById('question');
    const optionAElement = document.getElementById('option_a');
    const optionBElement = document.getElementById('option_b');
    const optionCElement = document.getElementById('option_c');
    const optionDElement = document.getElementById('option_d');
    const submitButton = document.getElementById('submit');
    const resultElement = document.getElementById('result');
    const explanationElement = document.getElementById('explanation');
    const nextButton = document.getElementById('next');

    let questions = [];
    let currentQuestion = null;
    let answeredQuestions = new Set();
    let currentIndex = 0;

    // Load questions from local JSON file
    async function loadQuestions() {
        const today = new Date().toISOString().split('T')[0];
        const response = await fetch(`Bank/DailyQuestions/${today}.json`);
        questions = await response.json();
        loadQuestion();
    }

    // Log result to Firebase storage
    async function logResult(result) {
        const logType = result.correct ? 'correct' : 'incorrect';
        const logRef = db.collection(logType).doc();
        await logRef.set(result);

        const attemptedRef = db.collection('attemptedquestions').doc();
        await attemptedRef.set(result);
    }

    // Submit answer
    submitButton.addEventListener('click', async () => {
        const selectedOption = document.querySelector('input[name="option"]:checked');
        if (selectedOption) {
            const isCorrect = selectedOption.value === currentQuestion.answer;
            resultElement.innerHTML = isCorrect ? 'Correct!' : 'Incorrect.';
            explanationElement.innerHTML = currentQuestion.explanation;
            MathJax.typeset(); // Render LaTeX

            // Store result locally and log to Firebase
            const result = {
                questionId: currentIndex - 1,
                correct: isCorrect,
                timestamp: new Date()
            };
            answeredQuestions.add(result);
            await logResult(result);

            questionContainer.classList.add('hidden');
            resultContainer.classList.remove('hidden');
        } else {
            alert('Please select an option.');
        }
    });

    // Load a new question
    function loadQuestion() {
        if (currentIndex >= questions.length) {
            alert('No more questions available.');
            return;
        }
        currentQuestion = questions[currentIndex];
        currentIndex++;

        // Display question
        questionElement.innerHTML = currentQuestion.question;
        optionAElement.innerHTML = currentQuestion.option_a;
        optionBElement.innerHTML = currentQuestion.option_b;
        optionCElement.innerHTML = currentQuestion.option_c;
        optionDElement.innerHTML = currentQuestion.option_d;
        MathJax.typeset(); // Render LaTeX

        questionContainer.classList.remove('hidden');
        resultContainer.classList.add('hidden');
    }

    // Submit answer
    submitButton.addEventListener('click', () => {
        const selectedOption = document.querySelector('input[name="option"]:checked');
        if (selectedOption) {
            const isCorrect = selectedOption.value === currentQuestion.answer;
            resultElement.innerHTML = isCorrect ? 'Correct!' : 'Incorrect.';
            explanationElement.innerHTML = currentQuestion.explanation;
            MathJax.typeset(); // Render LaTeX

            // Store result locally
            const result = {
                questionId: currentIndex - 1,
                correct: isCorrect,
                timestamp: new Date()
            };
            answeredQuestions.add(result);

            questionContainer.classList.add('hidden');
            resultContainer.classList.remove('hidden');
        } else {
            alert('Please select an option.');
        }
    });

    // Load next question
    nextButton.addEventListener('click', loadQuestion);

    // Load the questions initially
    loadQuestions();
});