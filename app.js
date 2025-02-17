async function getTodaysQuestion() {
    const today = new Date().toISOString().split('T')[0];
    const response = await fetch('questions.json'); // Updated path
    const questions = await response.json();
    return questions[today] || null;
}

async function displayQuestion() {
    const questionData = await getTodaysQuestion();
    if (questionData) {
        document.getElementById('question').innerText = questionData.question;
        const optionsContainer = document.getElementById('options');
        optionsContainer.innerHTML = '';
        questionData.options.forEach((option, index) => {
            const optionElement = document.createElement('div');
            optionElement.classList.add('option');
            optionElement.innerText = option;
            optionElement.onclick = () => selectOption(index);
            optionsContainer.appendChild(optionElement);
        });
    }
}

let selectedOptionIndex = null;
function selectOption(index) {
    const options = document.querySelectorAll('.option');
    options.forEach((option, i) => {
        option.classList.remove('selected');
        if (i === index) option.classList.add('selected');
    });
    selectedOptionIndex = index;
}

async function submitAnswer() {
    if (selectedOptionIndex === null) return alert('Please select an option');
    
    const questionData = await getTodaysQuestion();
    const feedbackContainer = document.getElementById('feedback');
    feedbackContainer.innerHTML = '';
    
    const isCorrect = selectedOptionIndex === questionData.answer;
    const selectedOptionElement = document.querySelector(`.option:nth-child(${selectedOptionIndex + 1})`);
    
    selectedOptionElement.classList.add(isCorrect ? 'correct' : 'incorrect');
    feedbackContainer.innerText = isCorrect ? 'Correct!' : 'Incorrect!';
}

// Event listeners
document.getElementById('submit-answer').addEventListener('click', submitAnswer);
window.addEventListener('load', displayQuestion);
