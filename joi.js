const Joi = require('joi'); 

const schema = Joi.object().keys( {
    username:Joi.string().alphanum().min(3).max(30).required(), 
    password:Joi.string().regex(/^[a - zA - Z0-9] {3, 30}$/), 
    access_token:[Joi.string(), Joi.number()], 
    birthyear:Joi.number().integer().min(0).max(10000), 
    email:Joi.string().email()
}).with('username', 'birthyear').without('password', 'access_token');

const  doSomethingWith= (value)=> console.log(`I do nothing with  ${JSON.stringify(value)}`);

Joi.validate( {username:'Loretta', birthyear:'9527'}, schema, function (err, value) {
    if (err) {
        console.log(err.details)}
    else {
        doSomethingWith(value)        
    }
}); 