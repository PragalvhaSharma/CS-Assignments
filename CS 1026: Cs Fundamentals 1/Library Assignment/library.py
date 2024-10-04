# Define a function called 'start' that initializes the program.
def start():
    # Define a list of books, each represented as a list of attributes.
    all_books = [
        ['9780596007126', "The Earth Inside Out", "Mike B", 2, ['Ali']],
        ['9780134494166', "The Human Body", "Dave R", 1, []],
        ['9780321125217', "Human on Earth", "Jordan P", 1, ['David', 'b1', 'user123']]
    ]

    # Define a list of weight factors used for ISBN validation.
    weight_factor = [1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1]

    # Create an empty list to store the weighted digits of ISBN.
    weighted_list = []

    # Create empty lists to store books that have been found and ISBNs that have been borrowed.
    books_found = []
    borrowed_isbns = []

    # Define a function to check if a given ISBN is in the book list.
    def is_isbn_in_list(new_isbn, book_list):
        for book in book_list:
            if book[0] == new_isbn:
                return True
        return False

    # Define a function to find a book's name if given the ISBN
    def find_book_name_by_isbn(isbn, book_list):
        for book in book_list:
            if book[0] == isbn:
                return book[1]  # Return the book name if ISBN is found
        return None  # Return None if ISBN is not found

    # Define a function to display the list of books used when user inputs 4 or 5.
    def display_books(book_list):
        # Go through the Books List and execute the command
        for element in range(len(book_list)):
            print("-" * 15)
            if book_list[element][0] in borrowed_isbns:
                print("[Unavailable]")
            else:
                print("[Available]")
                # Use the formatting technique taught in class to display information
            print(f"{book_list[element][1]} - {book_list[element][2]}")
            print(f"E: {book_list[element][3]} ISBN: {book_list[element][0]}")
            print(f"borrowed by: {book_list[element][4]}")

    # Define a function to add a new book to the list. Uses the input function to get the required info from user
    def add_a_new_book():
        new_book = input("Book> ")
        while "%" in new_book or "*" in new_book:  # Validate user Inputs
            print("Invalid Book Name!")
            new_book = input("Book> ")  # Keep asking until valid

        author_name = input("Author name> ")

        edition_number = input("Edition> ")
        while not edition_number.isdigit():  # Validate to see if it is a number or not
            print("Invalid Edition!")
            edition_number = input("Edition>")  # Keeps asking the user until a valid input is entered
        edition_number = int(edition_number)  # Conversion to stay consistent with the formatting of all_books

        isbn_number = input("ISBN> ")
        # Validate ISBN by checking the length and if it's a number or not
        while not isbn_number.isdigit() or len(isbn_number) != 13:
            print("Invalid ISBN!")
            # Keep asking until a valid input
            isbn_number = input("ISBN> ")
        else:
            # Convert characters inputted into int values and add them to the weighted_list
            for element in isbn_number:
                weighted_list.append(int(element))

            # Multiply the Values in the list by the weight factor
            for index in range(len(weight_factor)):
                weighted_list[index] *= weight_factor[index]

            # Check if the ISBN is valid
            weighted_sum = sum(weighted_list)
            if weighted_sum % 10 != 0:
                print("Invalid ISBN!")
                # Weighted list is cleared for future inputs
                weighted_list.clear()
                user_screen_opening()
            else:
                # Check for duplicates by calling on the is_isbn_in_list function
                if is_isbn_in_list(isbn_number, all_books):
                    print(f"Duplicate ISBN is found! Cannot add the book.")
                    weighted_list.clear()
                    user_screen_opening()
                    # Run the program again for different inputs
                else:
                    # Execute if no duplicates are found by updating all_books and displaying a message
                    all_books.append([isbn_number, new_book, author_name, edition_number, []])
                    print("A new book is added successfully.")
                    weighted_list.clear()
                    user_screen_opening()
                    # Run the program again for different inputs

    # Define a function to borrow a book.
    def borrow_a_book():
        # Ask for name and search term using the input function
        borrower_name = input("Enter the borrower name> ")
        search_term = input("Search term> ")

        # Check if * is in the last index
        if "*" in search_term[-1]:
            # Update search term to remove *
            search_term = (search_term[0:-1]).lower()
            # Run through the all_books list
            for info in range(len(all_books)):
                # Save the book information to a variable
                book_name = all_books[info][1]
                lower_text = book_name.lower()
                if search_term in lower_text and all_books[info][0] not in borrowed_isbns:
                    # Execute if a matching book has been found and the book has not been already borrowed
                    books_found.append(all_books[info][1])
                    all_books[info][4].append(borrower_name)
                    borrowed_isbns.append(all_books[info][0])

        # Check if % is in the search term
        elif "%" in search_term[-1]:
            # Update search term to remove %
            search_term = (search_term[0:-1]).lower()
            # Run through the all Books List
            for info in range(len(all_books)):
                # Save the book information to a variable
                book_name = all_books[info][1]
                lower_text = book_name[0:len(search_term)].lower()  # Save the beginning of the book name
                if search_term in lower_text and all_books[info][0] not in borrowed_isbns:
                    # Execute if the beginning of the book has been found, and it hasn't already been borrowed
                    books_found.append(all_books[info][1])
                    all_books[info][4].append(borrower_name)
                    borrowed_isbns.append(all_books[info][0])

        else:
            search_term = search_term.lower()
            # Run through the all Books List
            for info in range(len(all_books)):
                book_name = all_books[info][1]
                lower_text = book_name.lower()
                if search_term == lower_text and all_books[info][0] not in borrowed_isbns:
                    # Execute if the book has been found and is not already being borrowed
                    books_found.append(all_books[info][1])
                    all_books[info][4].append(borrower_name)
                    borrowed_isbns.append(all_books[info][0])

        # If no book is found, alert the user and start form the home screen
        if len(books_found) == 0:
            print("No books found!")
            user_screen_opening()
        # If a book is found, let the user know the books that are now being borrowed
        else:
            for element in books_found:
                print(f"-'{element}' is borrowed!")
            # Cleared for future use
            books_found.clear()
            user_screen_opening()

    # Define a function to return a borrowed book.
    def return_a_book():
        isbn_number = input("ISBN> ")
        # Check if the Book was actually borrowed
        if isbn_number in borrowed_isbns:
            # Run through all_books
            for index in range(len(all_books)):
                # Find matching ISBN
                if all_books[index][0] == isbn_number:
                    borrowed_isbns.remove(isbn_number)
            # Get the Books name through the ISBN by calling the find_book_name_by_isbn function and alert user
            book_name = find_book_name_by_isbn(isbn_number, all_books)
            print(f"'{book_name}' is returned.")
            user_screen_opening()
        else:
            # If no book is borrowed with the ISBN
            print("No book is found!")
            user_screen_opening()

    # Define a function to display the main user screen and execute all the functions above
    def user_screen_opening():
        # Print the Opening Screen and ask for input
        print('\n######################')
        print('1: (A)dd a new book.')
        print('2: Bo(r)row books.')
        print('3: Re(t)urn a book.')
        print('4: (L)ist all books.')
        print('5: E(x)it.')
        print('######################\n')
        user_input = input("Your selection> ")

        # If not a number then make it lower case
        if user_input.isdigit() is False:
            user_input = user_input.lower()

        # Check if the user_input is valid
        if user_input not in ["1", "2", "3", "4", "5", "a", "r", "t", "l", "x"]:
            print("Wrong selection! Please select a valid option.")
            user_screen_opening()

        # Since the user input is valid, check if it's 't' or '3'
        if user_input == "a" or user_input == "1":
            add_a_new_book()  # Calls functon to add a new book

        # Or else check if it's 'r' or '2'
        elif user_input == "r" or user_input == "2":
            borrow_a_book()  # Calls functon to borrow books

        # Or else check if it's 't' or '3'
        elif user_input == "t" or user_input == "3":
            return_a_book()  # Calls functon to return books

        # Or else check if it's 'l' or '4'
        elif user_input == "l" or user_input == "4":
            display_books(all_books)  # Calls function to display all the books
            user_screen_opening()  # Show the opening screen again after displaying the books

        # Or else check if it's 'X' or '5'
        elif user_input == "x" or user_input == "5":
            print("$$$$$$$$ FINAL LIST OF BOOKS $$$$$$$$")
            display_books(all_books)  # Calls function to display all the books

    # Call the user_screen_opening function to start the program.
    user_screen_opening()

# Call the start function to begin the program execution.


start()
