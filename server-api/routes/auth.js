
const express = require('express');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');

const router = express.Router();
const SECRET_KEY = 'YOUR_SUPER_SECRET_KEY'; 
let users = []; 


router.post('/register', async (req, res) => {
    const { username, password } = req.body;

    if (users.find(u => u.username === username)) {
        return res.status(400).send({ message: 'Numele de utilizator există deja.' });
    }

    try {
      
        const hashedPassword = await bcrypt.hash(password, 10);
        
        const newUser = {
            id: users.length + 1,
            username,
            password: hashedPassword, 
        };
        users.push(newUser);

        res.status(201).send({ message: 'Utilizator înregistrat cu succes.', userId: newUser.id });
    } catch (error) {
        res.status(500).send({ message: 'Eroare la înregistrare.' });
    }
});


router.post('/login', async (req, res) => {
    const { username, password } = req.body;
    const user = users.find(u => u.username === username);

    if (!user) {
        return res.status(401).send({ message: 'Nume de utilizator sau parolă incorectă.' });
    }

 
    const isValid = await bcrypt.compare(password, user.password);

    if (!isValid) {
        return res.status(401).send({ message: 'Nume de utilizator sau parolă incorectă.' });
    }


    const token = jwt.sign(
        { userId: user.id, username: user.username }, 
        SECRET_KEY, 
        { expiresIn: '1h' } 
    );

    res.status(200).send({ 
        message: 'Autentificare reușită.', 
        token: token 
    });
});

module.exports = router;