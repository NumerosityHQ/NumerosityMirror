function showLogin() {
    document.getElementById('login-form').style.display = 'block';
    document.getElementById('signup-form').style.display = 'none';
}

function showSignUp() {
    document.getElementById('login-form').style.display = 'none';
    document.getElementById('signup-form').style.display = 'block';
}

function login() {
    const username = document.getElementById('login-username').value;
    const password = document.getElementById('login-password').value;
    
    auth.signInWithEmailAndPassword(username + "@example.com", password)
        .then(() => {
            document.getElementById('auth-container').style.display = 'none';
            document.getElementById('question-container').style.display = 'block';
            document.getElementById('stats-container').style.display = 'block';
            document.getElementById('friends-container').style.display = 'block';
            loadQuestion();
        })
        .catch(error => {
            console.error("Error logging in: ", error);
        });
}

function signUp() {
    const username = document.getElementById('signup-username').value;
    const password = document.getElementById('signup-password').value;

    auth.createUserWithEmailAndPassword(username + "@example.com", password)
        .then(() => {
            login();
        })
        .catch(error => {
            console.error("Error signing up: ", error);
        });
}