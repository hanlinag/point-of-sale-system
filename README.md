# point-of-sale-system

This POS project is based on initial work by @hanlinag (https://github.com/hanlinag/point-of-sale-system)

This Point of Sale System is aimed for small convenience stores which are still unfamiliar to computerized system of running the business. This system is simple yet effective as the functions are easy to use for the users, that is, for the store owner or store manager and store cashiers.

In the admin panel, there are fields which are effective for the administration of the whole store. The admin can manage list of product items, product categories, cashier information, customer information and supplier information.

In the cashier panel, the user will be provided with interfaces to sell items to the customer. He/she can search the selling items by barcode or item-name. The cashier can also redeem the payment card or create new cards.

In card, we put each card’s QR-code. We included this function as we believe that the card payment system is more convenient than the cash payment. Being Cashless is made effortless.

This project is implemented with Model–view–controller (MVC) software design pattern.

For enduser use, check out the enduserguide.pdf file. 
Database .sql file is also included in the directory.

<hr>

# Libraries/Dependencies Used
- Java FX
- Jaspersoft iReport
- mySQL JDBC
- JFoenix for material design UI
- JDK 1.8

<hr>

# Features 
- User authorization (admin, cashier)
- Stock management (add/delete/modify inventory item)
- Create own barcode and QR code
- Customer cash card (provide discount)
- Easily create promotion
- Supplier management (company info, supply date, supply amount)
- Track popular item over customer interest (i.e. Most selling item)
- Report generation

<hr>

# Setup
1. Download the project source code.
2. Create SQL database named "ACPCEPOS"
3. Import ACPCEPOS.sql file to the database. 
4. Import the source code to the IDE, either from zip or directly clone from URL. (We used Eclipse/VS Code to develop this project)
5. Import the Jar dependecies to the project build path.
6. Install Jasper iReport from eclipse marketplace (https://community.jaspersoft.com/project/ireport-designer) 
7. Install proper sql driver lib based on your environment and edit DBInitialize.2 with your appropriate server location
8. Run the project. Use JRE 1.8 or Zulu 15 JDK to prevent errors as JavaFX isn't part of the standard JDK from 11
9. Admin username: posACPCE; pw: admin123.
10. Now, you're good to go!!! Modify it for your own usecase. 

<hr>

# Diagrams
System flowchart (Admin)
![alt text](https://raw.githubusercontent.com/spandu500/point-of-sale-system/master/images/adminflowchart.png)

System flowchart (Cashier)
![alt text](https://github.com/spandu500/point-of-sale-system/blob/master/images/cashierflowchart.png?raw=true)

Usecase Diagram
![alt text](https://github.com/spandu500/point-of-sale-system/blob/master/images/ucscasefinal.png?raw=true)


ER Diagram
![alt text](https://github.com/spandu500/point-of-sale-system/blob/master/images/ER%20Final.png?raw=true)

<hr>

# Screenshots
Home Screen
![alt text](https://github.com/spandu500/point-of-sale-system/blob/master/images/home.png?raw=true)

Cashier Panel
![alt text](https://github.com/spandu500/point-of-sale-system/blob/master/images/cashier.png?raw=true)

Card Payment
![alt text](https://github.com/spandu500/point-of-sale-system/blob/master/images/cardpayment.png?raw=true)

Admin Panel
![alt text](https://github.com/spandu500/point-of-sale-system/blob/master/images/admin.png?raw=true)

Chart View
![alt text](https://github.com/spandu500/point-of-sale-system/blob/master/images/chart.png?raw=true)


<hr>

# Report Samples
Voucher Sample
![alt text](https://github.com/spandu500/point-of-sale-system/blob/master/images/voucher.png?raw=true)

Daily Report Sample
![alt text](https://github.com/spandu500/point-of-sale-system/blob/master/images/dailysale.png?raw=true)

Monthly Report Sample
![alt text](https://github.com/spandu500/point-of-sale-system/blob/master/images/monthlysale.png?raw=true)

Popular Item Sample
![alt text](https://github.com/spandu500/point-of-sale-system/blob/master/images/popularitem.png?raw=true) 


<hr>

# Conclusion
This system is user-friendly and reliable computer based standalone system for mini-convenience stores. It has been designed to manage the whole store’s information and general reports (daily, monthly, popular items). It is capable of managing product items, product categories, cashier information, customer information, card information and supplier information. It is also available for calculating promotions, viewing popular items and sale charts. The developed system provides solution to the manual convenience stores’ problems and so provides special functions such as using the card payment system, calculating promotion or viewing sale charts. The software offers stability, cost-effectiveness and usability. It provides the most flexible and adaptable standard management system solutions for convenience stores.

# LICENSE
[MIT License](LICENSE)

