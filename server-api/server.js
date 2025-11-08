
const express = require('express');
const bodyParser = require('body-parser');
const authRoutes = require('./routes/auth');
const taskRoutes = require('./routes/tasks');

const app = express();
const PORT = 3000;


app.use(bodyParser.json());


app.use('/api/auth', authRoutes); 


app.use('/api/tasks', taskRoutes); 

app.get('/', (req, res) => {
    res.send('Task Manager Minimal API Rulează!');
});

app.listen(PORT, () => {
    console.log(`Serverul rulează pe http://localhost:${PORT}`);
});