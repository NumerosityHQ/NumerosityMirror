// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
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
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);