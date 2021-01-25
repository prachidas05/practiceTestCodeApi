#About

The warehouse software has Inventory , Products and the Bill of Materials.
Through the Rest end points the software can perform the below four functionalities:
1)Add articles to Inventory
2)Add products to the Warehouse
3)Retrieve product overview available in the inventory
4)Sell a product and update the Inventory

#Technology Stack

Java, Maven, SpringBoot, Hibernate, h2 DB, Mockito
(Compatible with Java 8 and onwards)

# Getting Started

Launch the SpringBoot Application WarehouseApplication.java in your local.

### End Points
Post:Add articles to Inventory (Input:inventory.json)
http://localhost:8080/warehouseapi/articles

Post:Add products to the Warehouse:(Input:products.json)
http://localhost:8080/warehouseapi/products

Get:Retrieve product overview available in the inventory:
http://localhost:8080/warehouseapi/products/overview

Post:Sell a product and update the Inventory:
http://localhost:8080/warehouseapi/product/sold?name=Dining Chair&quantity=1

