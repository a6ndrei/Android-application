
const express = require('express');
const protect = require('../middleware/authMiddleware'); 

const router = express.Router();

let tasks = []; 


router.use(protect); 


router.post('/', (req, res) => {
    const { title, description, delegatedTo, collaborators } = req.body;
    
  
    const newTask = {
        id: tasks.length + 1,
        ownerId: req.user.userId, 
        title,
        description,
        status: 'pending', 
        delegatedTo: delegatedTo || null,
        collaborators: collaborators || [],
        createdAt: new Date(),
    };
    
    tasks.push(newTask);
    res.status(201).send({ message: 'Task creat cu succes.', task: newTask });
});


router.get('/', (req, res) => {
    const userId = req.user.userId;

  
    const userTasks = tasks.filter(task => 
        task.ownerId === userId || 
        task.delegatedTo === userId || 
        task.collaborators.includes(userId)
    );

    res.status(200).send(userTasks);
});



module.exports = router;