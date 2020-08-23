# point-of-sale-system
This Point of Sale System is aimed for small convenience stores which are still unfamiliar to computerized system of running the business. This system is simple yet effective as the functions are easy to use for the users, that is, for the store owner or store manager and store cashiers.

In the admin panel, there are fields which are effective for the administration of the whole store. The admin can manage list of product items, product categories, cashier information, customer information and supplier information.

In the cashier panel, the user will be provided with interfaces to sell items to customers. He/she can search the selling items by barcode or item-name. The cashier can also redeem the payment card or create new cards.

In card, we put each card’s QR-code. We included this function because we believe that the card payment system is more convenient than the cash payment. Cashless made effortless.

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
2. Create SQL database named "UCSMPOS"
3. Import the UCSMPOS.sql file to the database. 
4. Import the source code project to the IDE. (I used Eclipse to develop this project.)
5. Import the Jar library files to the project build path.
6. Go to the market place in Eclipse. Install Jasper iReport (https://community.jaspersoft.com/project/ireport-designer) library. 
7. Run the project. (If the project with login.java cannot find the main class/method, create new Java project with the same name and copy all the files in the new project.)
8. If Database connection is not establish, run the following command into your SQL console:
```
mysql> set @@global.show_compatibility_56=ON;
```
9. Admin username and password: usernaem: pos-2018-ad; pw: admin@2018.
10. Now, you're good to go!!! Modify it for your own usecase. 

<hr>

# Barcode/QRcode scanner
- We developed Barcode/QR code scanner android app that scans the Barcode/QRcode from the item and sent that code to the computer via TCP. You can find the android app repository here : https://github.com/hanlinag/pos-barcode-scanner.git 
![alt text](https://github.com/hanlinag/point-of-sale-system/blob/master/images/barcode.png?raw=true)

# Diagrams
System flowchart (Admin)
![alt text](https://raw.githubusercontent.com/hanlinag/point-of-sale-system/master/images/adminflowchart.png)

System flowchart (Cashier)
![alt text](https://github.com/hanlinag/point-of-sale-system/blob/master/images/cashierflowchart.png?raw=true)

Usecase Diagram
![alt text](https://github.com/hanlinag/point-of-sale-system/blob/master/images/ucscasefinal.png?raw=true)


ER Diagram
![alt text](https://github.com/hanlinag/point-of-sale-system/blob/master/images/ER%20Final.png?raw=true)

<hr>

# Screenshots
Home Screen
![alt text](https://github.com/hanlinag/point-of-sale-system/blob/master/images/home.png?raw=true)

Cashier Panel
![alt text](https://github.com/hanlinag/point-of-sale-system/blob/master/images/cashier.png?raw=true)

Card Payment
![alt text](https://github.com/hanlinag/point-of-sale-system/blob/master/images/cardpayment.png?raw=true)

Admin Panel
![alt text](https://github.com/hanlinag/point-of-sale-system/blob/master/images/admin.png?raw=true)

Chart View
![alt text](https://github.com/hanlinag/point-of-sale-system/blob/master/images/chart.png?raw=true)


<hr>

# Report Samples
Voucher Sample
![alt text](https://github.com/hanlinag/point-of-sale-system/blob/master/images/voucher.png?raw=true)

Daily Report Sample
![alt text](https://github.com/hanlinag/point-of-sale-system/blob/master/images/dailysale.png?raw=true)

Monthly Report Sample
![alt text](https://github.com/hanlinag/point-of-sale-system/blob/master/images/monthlysale.png?raw=true)

Popular Item Sample
![alt text](https://github.com/hanlinag/point-of-sale-system/blob/master/images/popularitem.png?raw=true) 


<hr>

# Gift Card Design
Front side
![alt text](https://github.com/hanlinag/point-of-sale-system/blob/master/images/cardfinalfront.png?raw=true)

Back side
![alt text](https://github.com/hanlinag/point-of-sale-system/blob/master/images/cardfinalback.png?raw=true)
<hr>

# Conclusion
This system is user-friendly and reliable computer based standalone system for mini-convenience stores. It has been designed to manage the whole store’s information and general reports (daily, monthly, popular items). It is capable of managing product items, product categories, cashier information, customer information, card information and supplier information. It is also available for calculating promotions, viewing popular items and sale charts. The developed system provides solution to the manual convenience stores’ problems and so provides special functions such as using the card payment system, calculating promotion or viewing sale charts. The software offers stability, cost-effectiveness and usability. It provides the most flexible and adaptable standard management system solutions for convenience stores.

# LICENSE
[MIT License](LICENSE)

