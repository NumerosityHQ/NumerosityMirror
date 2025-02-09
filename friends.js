function addFriend() {
    const friendUsername = document.getElementById('add-friend-username').value;
    const user = auth.currentUser;
    const friendsRef = db.collection('friends').doc(user.uid);
    friendsRef.update({
        friends: firebase.firestore.FieldValue.arrayUnion(friendUsername)
    }).then(() => {
        loadFriends();
    });
}

function removeFriend(friendUsername) {
    const user = auth.currentUser;
    const friendsRef = db.collection('friends').doc(user.uid);
    friendsRef.update({
        friends: firebase.firestore.FieldValue.arrayRemove(friendUsername)
    }).then(() => {
        loadFriends();
    });
}

function loadFriends() {
    const user = auth.currentUser;
    const friendsRef = db.collection('friends').doc(user.uid);
    friendsRef.get().then(doc => {
        if (doc.exists) {
            const friends = doc.data().friends;
            const friendsList = document.getElementById('friends-list');
            friendsList.innerHTML = "";
            friends.forEach(friend => {
                const friendElement = document.createElement('div');
                friendElement.innerText = friend;
                const removeButton = document.createElement('button');
                removeButton.innerText = 'Remove';
                removeButton.onclick = () => removeFriend(friend);
                friendElement.appendChild(removeButton);

                // Load friend's stats
                const friendStatsRef = db.collection('userStats').doc(friend);
                friendStatsRef.get().then(doc => {
                    if (doc.exists) {
                        const stats = doc.data();
                        const statsElement = document.createElement('div');
                        statsElement.innerText = `Curiosity Streaks: ${stats.curiosityStreaks}, Genius Streaks: ${stats.geniusStreaks}`;
                        friendElement.appendChild(statsElement);
                    }
                });

                friendsList.appendChild(friendElement);
            });
        }
    });
}

auth.onAuthStateChanged(user => {
    if (user) {
        loadFriends();
    }
});