# usdafooddb

This application provides a REST interface for retrieving USDA food products based on one or more search keywords.

Sample invocation:

Searching against product name:

http://<ipaddress>:<port>/usdafood/Product.action?name=Peanuts

Searching against ingredients:

http:///<ipaddress>:<port>/usdafood/Ingredients.action?name=sugar@salt

Database:

MySQL database backend with the below tables

product table
product image table
