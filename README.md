# point-of-sale-system
UCSM Point of Sale System is aimed for small convenience stores which are still unfamiliar to computerized system of running the business. This system is simple yet effective as the functions are easy to use for the users, that is, for the store owner or store manager and store cashiers.

In the admin panel, there are fields which are effective for the administration of the whole store. The admin can manage list of product items, product categories, cashier information, customer information and supplier information.

In the cashier panel, the user will be provided with interfaces to sell items to customers. He/she can search the selling items by barcode or item-name. The cashier can also redeem the payment card or create new cards.

In card, we put each card’s QR-code. We included this function because we believe that the card payment system is more convenient than the cash payment. Cashless made effortless.

This project is implemented with Model–view–controller (MVC) software design pattern.

For enduser use, check out the enduserguide.pdf file. 
Database .sql file is also included in the directory.

<hr>

# Libraries/Dependencies Used
- Java FX
- Jasper iReport
- mySQL 

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

# Barcode/QRcode scanner
- We developed Barcode/QR code scanner android app that scans the Barcode/QRcode from the item and sent that code to the computer via TCP. You can find the android app repository here : 
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


<hr>

# Report Samples


<hr>

# Gift Card Design
Front side
![alt text]()

Back side
![alt text]()
<hr>

# Conclusion
This system is user-friendly and reliable computer based standalone system for mini-convenience stores. It has been designed to manage the whole store’s information and general reports (daily, monthly, popular items). It is capable of managing product items, product categories, cashier information, customer information, card information and supplier information. It is also available for calculating promotions, viewing popular items and sale charts. The developed system provides solution to the manual convenience stores’ problems and so provides special functions such as using the card payment system, calculating promotion or viewing sale charts. The software offers stability, cost-effectiveness and usability. It provides the most flexible and adaptable standard management system solutions for convenience stores.