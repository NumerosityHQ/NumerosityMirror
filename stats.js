function updateStats(correct) {
    const user = auth.currentUser;
    const userStatsRef = db.collection('userStats').doc(user.uid);
    userStatsRef.get().then(doc => {
        if (doc.exists) {
            const stats = doc.data();
            stats.curiosityStreaks++;
            if (correct) stats.geniusStreaks++;
            stats.timePerQuestion.push({ date: new Date().toLocaleDateString("en-US"), time: 0 }); // Add time taken to solve
            stats.questionTypes.push(currentQuestion.Type);
            userStatsRef.update(stats).then(() => {
                displayStats(stats);
            });
        } else {
            const newStats = {
                curiosityStreaks: 1,
                geniusStreaks: correct ? 1 : 0,
                timePerQuestion: [{ date: new Date().toLocaleDateString("en-US"), time: 0 }],
                questionTypes: [currentQuestion.Type]
            };
            userStatsRef.set(newStats).then(() => {
                displayStats(newStats);
            });
        }
    });
}

function displayStats(stats) {
    document.getElementById('streaks').innerText = `Curiosity Streaks: ${stats.curiosityStreaks}, Genius Streaks: ${stats.geniusStreaks}`;

    const ctx = document.getElementById('time-per-question-chart').getContext('2d');
    const labels = stats.timePerQuestion.map(entry => entry.date);
    const data = stats.timePerQuestion.map(entry => entry.time);
    new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: 'Time per Question (seconds)',
                data: data,
                borderColor: 'white',
                backgroundColor: 'rgba(255, 255, 255, 0.2)'
            }]
        },
        options: {
            scales: {
                x: {
                    beginAtZero: true
                },
                y: {
                    beginAtZero: true
                }
            }
        }
    });

    document.getElementById('question-types').innerText = `Types of Questions Solved Right: ${stats.questionTypes.join(", ")}`;
}

function shareStats() {
    const statsContainer = document.getElementById('stats-container');
    html2canvas(statsContainer).then(canvas => {
        const imgData = canvas.toDataURL('image/png');
        const link = document.createElement('a');
        link.href = imgData;
        link.download = 'stats.png';
        link.click();
    });
}