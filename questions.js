let currentQuestion = null;

function loadQuestion() {
    const today = new Date().toISOString().split('T')[0]; // Get current date in YYYY-MM-DD format
    db.collection("questions").where("Date", "==", today).get()
        .then(querySnapshot => {
            querySnapshot.forEach(doc => {
                currentQuestion = doc.data();
                displayQuestion(currentQuestion);
            });
        })
        .catch(error => {
            console.error("Error loading question: ", error);
        });
}

function displayQuestion(question) {
    document.getElementById('question').innerText = question.Question;
    const options = question.Options.split(",");
    const optionsContainer = document.getElementById('options');
    optionsContainer.innerHTML = "";
    options.forEach(option => {
        const optionElement = document.createElement('div');
        optionElement.innerText = option;
        optionElement.onclick = () => selectOption(optionElement, option);
        optionsContainer.appendChild(optionElement);
    });
}

function selectOption(element, option) {
    const selected = document.querySelector('#options .selected');
    if (selected) selected.classList.remove('selected');
    element.classList.add('selected');
    currentQuestion.selectedOption = option;
}

function submitAnswer() {
    if (!currentQuestion.selectedOption) return alert("Please select an option.");
    const correct = currentQuestion.selectedOption == currentQuestion.Answer;
    const selectedElement = document.querySelector('#options .selected');
    if (correct) {
        selectedElement.classList.add('correct');
    } else {
        selectedElement.classList.add('wrong');
    }
    updateStats(correct);
    setTimeout(loadQuestion, 3000); // load next question after 3 seconds
}