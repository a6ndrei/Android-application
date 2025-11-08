
const jwt = require('jsonwebtoken');

const SECRET_KEY = 'YOUR_SUPER_SECRET_KEY'; 

const protect = (req, res, next) => {
  
    const authHeader = req.headers.authorization;

    if (!authHeader || !authHeader.startsWith('Bearer ')) {
        return res.status(401).send({ message: 'Acces neautorizat. Token lipsește sau este invalid.' });
    }

    const token = authHeader.split(' ')[1]; // Ia partea de după "Bearer "

    try {
       
        const decoded = jwt.verify(token, SECRET_KEY);
        
      
        req.user = decoded; 
        
      
        next();
    } catch (error) {
        return res.status(401).send({ message: 'Token invalid sau expirat.' });
    }
};

module.exports = protect;