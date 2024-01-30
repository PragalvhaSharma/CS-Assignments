# Create the Product Class for individual product objects
class Product:
    def __init__(self, name, price, category):
        # Initialize product attributes
        self._name = name  # Set product name
        self._price = price  # Set product price
        self._category = category  # Set product category

    # Define how products are compared for equality. Assuming all three attributes are the same.
    def __eq__(self, other):
        if isinstance(other, Product):
            # Check if the name, price, and category are the same for both products
            if ((self._name == other._name and self._price == other._price) and (self._category == other._category)):
                return True  # Products are equal
            else:
                return False  # Products are not equal
        else:
            return False  # If the other object is not a Product, they're not equal

    # get_name method returns the product name
    def get_name(self):
        return self._name

    # get_price method returns the product price
    def get_price(self):
        return self._price

    # get_category returns the product category
    def get_category(self):
        return self._category

    # Implement string representation of the Product object to showcase information
    def __repr__(self):
        rep = 'Product(' + self._name + ',' + str(self._price) + ',' + self._category + ')'  # String representation
        return rep


#Create the Inventory Class for indidivdual inventory objects
class Inventory:
    # Initialize an empty dictionary to store items with their details
    def __init__(self):
        self._itemInfo = {}

    # add_to_productInventory method adds a new product to the inventory with its price and quantity
    def add_to_productInventory(self, productName, productPrice, productQuantity):
        self._itemInfo[productName] = {"Price": productPrice, "Quantity": productQuantity}

    # add_productQuantity method increases the quantity of a specific product in the inventory
    def add_productQuantity(self, nameProduct, addQuantity):
        self._itemInfo[nameProduct]["Quantity"] += addQuantity

    # remove_productQuantity method decreases the quantity of a specific product in the inventory
    def remove_productQuantity(self, nameProduct, removeQuantity):
        self._itemInfo[nameProduct]["Quantity"] -= removeQuantity

    # get_productPrice method returns the price of a specific product from the inventory
    def get_productPrice(self, nameProduct):
        return self._itemInfo[nameProduct]["Price"]

    # get_productQuantity method returns the quantity of a specific product from the inventory
    def get_productQuantity(self, nameProduct):
        return self._itemInfo[nameProduct]["Quantity"]

    # display_Inventory method showcases the entire inventory with product names, prices, and quantities
    def display_Inventory(self):
        # Iterate through the products from the dictionary that stores item and their info
        for product in self._itemInfo:
            itemPrice = self._itemInfo[product]["Price"]
            itemQuantity = self._itemInfo[product]["Quantity"]
            print(f"{product}, {itemPrice}, {itemQuantity}")

#Create the ShoppingCart class for indidivdual Shopping Cart objects
class ShoppingCart:
    # Initialize a shopping cart with the buyer's name and access to the inventory items
    def __init__(self, buyerName, inventory):
        self._buyerName = buyerName # Save buyers name to an instance variable for later use
        self._inventory = inventory._itemInfo  # Access inventory items directly
        self._cartItems = {}  # Initialize an empty dictionary to store items in the cart

    # add_to_cart method adds a product to the cart with a requested quantity
    def add_to_cart(self, nameProduct, requestedQuantity):
        # Save the current quantity in a variable
        currentQuantity = self._inventory[nameProduct]["Quantity"]

        # Check if the requested quantity is valid
        if currentQuantity >= requestedQuantity:
            # Update Inventory Quantity
            currentQuantity -= requestedQuantity
            self._inventory[nameProduct]["Quantity"] = currentQuantity

            # Check if product is new or already in the cart
            if nameProduct in self._cartItems:
                # Add quantity to an item already in the cart
                self._cartItems[nameProduct] += requestedQuantity
            else:
                # Add new product to the cart with given quantity
                self._cartItems[nameProduct] = requestedQuantity
            return "Filled the order"   #Inform user indicating a success of the request
        else:
            return "Can not fill the order" #Inform user indicating failure

    # remove_from_cart method removes a product from the cart with a requested quantity
    def remove_from_cart(self, nameProduct, requestedQuantity):
        # Check if product is in the cart
        if nameProduct in self._cartItems:
            # Check if the requestedQuantity is valid
            if self._cartItems[nameProduct] < requestedQuantity:
                return "The requested quantity to be removed from cart exceeds what is in the cart"
            else:
                # Update Inventory Quantity
                self._cartItems[nameProduct] -= requestedQuantity
                self._inventory[nameProduct]["Quantity"] += requestedQuantity
                # Inform user indicating a success of the request
                return "Successful"
        else:
            # Inform user indicating failure
            return "Product not in the cart"

    # view_cart method showcases all items in the cart and calculates the total cost
    def view_cart(self):
        # Initially set the total cost to 0
        itemTotal = 0
        for items in self._cartItems:
            # Display items and their quantities in the cart
            print(items, self._cartItems[items])
            # Calculate and accumulate the total cost of items in the cart
            itemTotal += self._inventory[items]["Price"] * self._cartItems[items]
        # Display the total cost of items in the cart
        print(f"Total: {itemTotal}")
        # Display the buyer's name
        print(f"Buyer Name: {self._buyerName}")


#Create the ProductCatalog class for indidivdual product catalog objects
class ProductCatalog:
    def __init__(self):
        # Initialize the ProductCatalog with instance variables
        self._allProducts = []  # List to store all products
        self._low_prices = set()  # Set to store products with low prices
        self._medium_prices = set()  # Set to store products with medium prices
        self._highPrices = set()  # Set to store products with high prices

    # addProduct method adds a product to the catalog
    def addProduct(self, product):
        self._allProducts.append(product) # Update the list with all the products

    # price_category method categorizes products based on their prices into low, medium, and high price sets
    def price_category(self):
        # Iterate through all the products in the _allProducts list
        for product in self._allProducts:
            price = product.get_price()
            # Categorize products based on their prices
            if 0 <= price <= 99:
                self._low_prices.add(product.get_name())  # Add product to low price set
            elif 100 <= price <= 499:
                self._medium_prices.add(product.get_name())  # Add product to medium price set
            else:
                self._highPrices.add(product.get_name())  # Add product to high price set

        # Display the number of products in each price category
        print(f"Number of low price items: {len(self._low_prices)}")
        print(f"Number of medium price items: {len(self._medium_prices)}")
        print(f"Number of high price items: {len(self._highPrices)}")

    # display_catalog method showcases details of all products in the catalog
    def display_catalog(self):
        # Iterate through all the products in the ._allProducts list
        for products in self._allProducts:
            # Display Catalog with the following format
            print(f"Product: {products.get_name()} Price: {products.get_price()} "
                  f"Category: {products.get_category()}")


# populate_inventory purpose: Read data from a csv file and create a product inventory.
def populate_inventory(filename):
    # Create an object by calling upon the Inventory() Class
    productInventory = Inventory()

    # Open the file containing inventory data
    inventoryFile = open(filename, "r")

    # Read each line in the file and add product information to the inventory
    fileLine = inventoryFile.readline()
    while fileLine != "":
        # Split the line into individual pieces of data
        lineData = fileLine.strip().split(",")

        # Add product information to the inventory
        productInventory.add_to_productInventory(lineData[0], int(lineData[1]), int(lineData[2]))

        # Read the next line
        fileLine = inventoryFile.readline()

    # Close the file after reading
    inventoryFile.close()

    # Return the populated product inventory
    return productInventory

# populate_catalog purpose: Read data from a csv file and create a product catalog
def populate_catalog(fileName):
    # Create an object by calling on the ProductCatalog() class
    allProductCatalog = ProductCatalog()

    # Open the file containing catalog data
    inventoryFile = open(fileName, "r")

    # Read each line in the file and add product information to the catalog
    fileLine = inventoryFile.readline()
    while fileLine != "":
        # Split the line into individual pieces of data
        lineData = fileLine.split(",")

        # Create a Product object using the data from the file
        productObj = Product(lineData[0], int(lineData[1]), lineData[3].strip())  # Assuming index 3 is the category

        # Add the Product object to the product catalog
        allProductCatalog.addProduct(productObj)

        # Read the next line
        fileLine = inventoryFile.readline()

    # Close the file after reading
    inventoryFile.close()

    # Return the populated product catalog
    return allProductCatalog

