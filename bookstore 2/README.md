# Bookstore Management System

A Spring Boot application demonstrating dependency injection, layered architecture, and common annotations through a bookstore management system.

## Project Overview

This application simulates a bookstore with two primary modes of operation:

- **Kiosk Mode**: Customer-facing checkout and sales interface
- **Admin Mode**: Inventory management and administrative functions

The project serves as a practical introduction to Spring Boot concepts including:

- Dependency Injection and Inversion of Control
- Layered Architecture (Controller, Service, Repository)
- Spring Boot Annotations
- Data Transfer Objects and Models
- Error Handling and Result Patterns

## Models

### Book

The core entity representing a book in the bookstore inventory.

```java
public record Book(String isbn, String title, String author, String genre)
```

**Fields:**

- `isbn`: Unique identifier for the book
- `title`: Book title
- `author`: Author name
- `genre`: Book category (Fantasy, Science Fiction, Programming, etc.)

### InventoryItem

Represents a book in the store's inventory with stock quantity and pricing.

```java
public class InventoryItem {
    private final Book book;
    private int quantity;
    private BigDecimal price;
}
```

**Key Features:**

- Immutable book reference
- Mutable quantity and price with validation
- Prevents negative quantities and prices
- Uses `BigDecimal` for precise monetary calculations

### CartItem

Represents a book added to a customer's shopping cart.

```java
public class CartItem {
    private final Book book;
    private int quantity;
    private final BigDecimal price;
    private BigDecimal extendedPrice;
}
```

**Key Features:**

- Automatic extended price calculation (quantity × unit price)
- Immutable book and unit price references
- Quantity validation
- Precision handling with `RoundingMode.HALF_UP`

### Result\<T>

A generic wrapper class for operation results, providing consistent error handling.

```java
public class Result<T> {
    private boolean success;
    private String message;
    private T data;
}
```

**Usage:**

- Encapsulates operation success/failure status
- Provides descriptive messages
- Carries result data when successful
- Enables functional-style error handling

## Sample Data

The application includes a diverse inventory of 19 books across three genres:

```
=== ALL INVENTORY ITEMS ===
═══════════════════════════════════════════════════════════════════════════
                              INVENTORY ITEMS
═══════════════════════════════════════════════════════════════════════════
ISBN               TITLE                AUTHOR          GENRE        QTY        PRICE
───────────────────────────────────────────────────────────────────────────
0010               Ender's Game         Orson Scott ... Science F...  11       $ 14.99
0006               Dune                 Frank Herbert   Science F...   9       $ 17.99
0017               Java: The Complet... Herbert Schildt Programming    8       $ 59.99
0007               Foundation           Isaac Asimov    Science F...   7       $ 13.99
0018               Learning Python      Mark Lutz       Programming    4       $ 64.99
0004               The Way of Kings     Brandon Sand... Fantasy        6       $ 18.99
0015               The Pragmatic Pro... David Thomas... Programming    5       $ 47.99
0005               The Name of the Wind Patrick Roth... Fantasy       10       $ 15.99
0016               Head First Design... Eric Freeman    Programming    7       $ 44.99
0002               The Fellowship of... J.R.R. Tolkien  Fantasy        8       $ 14.99
0013               Clean Code           Robert C. Ma... Programming    6       $ 49.99
0003               A Game of Thrones    George R.R. ... Fantasy       12       $ 16.99
0014               Effective C++        Scott Meyers    Programming    3       $ 52.99
0011               The Martian          Andy Weir       Science F...  13       $ 16.99
0001               The Hobbit           J.R.R. Tolkien  Fantasy       10       $ 12.99
0012               Effective Java       Joshua Bloch    Programming    4       $ 54.99
0008               The Hitchhiker's ... Douglas Adams   Science F...  14       $ 12.99
0019               Introduction to A... Thomas H. Co... Programming    2       $ 89.99
0009               Neuromancer          William Gibson  Science F...   5       $ 15.99
═══════════════════════════════════════════════════════════════════════════
ℹ Total items in inventory: 19
```

# Repository Layer

The repository layer demonstrates several key Spring Boot concepts, including dependency injection, configuration-driven bean selection, property binding, and lifecycle management with `@PostConstruct`.

## Repository Interface

The `InventoryRepository` interface defines the contract for data access operations:

```java
public interface InventoryRepository {
    List<InventoryItem> getAll();
    List<InventoryItem> getInStock();
    void add(InventoryItem item);
    void update(InventoryItem item);
    void delete(String isbn);
    InventoryItem getByIsbn(String isbn);
}
```

**Key Benefits:**

- **Abstraction**: Service layer depends on interface, not concrete implementation
- **Testability**: Easy to mock for unit testing
- **Flexibility**: Can swap implementations without changing business logic
- **Polymorphism**: Multiple implementations can be used interchangeably

## Implementation Strategies

### 1. InMemoryInventoryRepository

A simple in-memory implementation perfect for development and testing:

```java
public class InMemoryInventoryRepository implements InventoryRepository {
    private final Map<String, InventoryItem> inventory = new HashMap<>();
    
    public InMemoryInventoryRepository() {
        initializeSampleData();
    }
}
```

**Characteristics:**

- Pre-populated with sample data
- Fast access with `HashMap` storage
- Data doesn't persist between application restarts
- Ideal for prototyping and testing

### 2. CsvInventoryRepository

A file-based implementation demonstrating persistence and configuration:

java

```java
public class CsvInventoryRepository implements InventoryRepository {
    private final Map<String, InventoryItem> inventory = new HashMap<>();
    
    @Value("${inventory.csv.filepath:data/inventory.csv}")
    private String filename;
    
    @PostConstruct
    public void init() {
        loadFromFile();
    }
}
```

**Key Features:**

- **Property Injection**: Configurable file path via `@Value`
- **Default Values**: Falls back to `data/inventory.csv` if not specified
- **Lifecycle Management**: Uses `@PostConstruct` for initialization
- **Persistence**: Automatically saves changes to CSV file

## Configuration-Driven Bean Selection

The `InventoryConfig` class demonstrates how to conditionally create objects based on application properties:

```java
@Configuration
public class InventoryConfig {
    
    @Value("${inventory.repository.type:memory}")
    private String repositoryType;
    
    @Bean
    public InventoryRepository inventoryRepository() {
        switch (repositoryType.toLowerCase()) {
            case "csv":
                return new CsvInventoryRepository();
            case "memory":
                return new InMemoryInventoryRepository();
            default:
                throw new IllegalArgumentException(
                    "Invalid repository type: " + repositoryType);
        }
    }
}
```

### Configuration Properties

Set the repository type in `application.properties`:

```properties
# Inventory Repository Configuration
# Valid values: memory, csv
inventory.repository.type=csv

# CSV Inventory Repository Configuration
inventory.csv.filepath=data/inventory.csv
```

## Spring Boot Lifecycle: @PostConstruct Explained

The CSV repository demonstrates an important Spring Boot concept - why `@PostConstruct` is necessary:

### The Problem

java

```java
// This WON'T work as expected
public CsvInventoryRepository() {
    loadFromFile(); // filename is null - @Value not injected yet!
}
```

### The Solution

```java
@Value("${inventory.csv.filepath:data/inventory.csv}")
private String filename;

@PostConstruct
public void init() {
    loadFromFile(); // filename is properly injected now, init() is invoked after the constructor
}
```

### Spring Bean Lifecycle Order

1. **Object Creation**: Constructor called, object instantiated
2. **Dependency Injection**: `@Autowired` and `@Value` fields populated
3. **Post-Processing**: `@PostConstruct` methods executed
4. **Bean Ready**: Bean is ready for use

**Why This Matters:**

- Properties from `@Value` are not available in constructors
- `@PostConstruct` ensures all dependencies are injected before initialization
- Critical for any setup that depends on injected values

## File Operations in CSV Repository

### Loading Data

We use the buffered reader to read line-by-line.

```java
private void loadFromFile() {
    File file = new File(filename);
    if (!file.exists()) {
        return; // Start with empty inventory
    }
    
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            // Parse CSV line and create InventoryItem
        }
    } catch (IOException | NumberFormatException e) {
        throw new RuntimeException("Error reading from file: " + filename, e);
    }
}
```

### Saving Data

The print writer allows easy formatting for converting inventory items to CSV lines.

```java
private void saveToFile() {
    try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
        for (InventoryItem item : inventory.values()) {
            Book book = item.getBook();
            writer.printf("%s,%s,%s,%s,%d,%.2f%n",
                    book.isbn(), book.title(), book.author(),
                    book.genre(), item.getQuantity(), item.getPrice());
        }
    } catch (IOException e) {
        throw new RuntimeException("Error writing to file: " + filename, e);
    }
}
```

## Error Handling Strategies

Both implementations include basic error handling:

### Input Validation

```java
@Override
public void add(InventoryItem item) {
    if (item == null) {
        throw new IllegalArgumentException("Item cannot be null");
    }
    inventory.put(item.getBook().isbn(), item);
    // CSV implementation also saves to file
}
```

### Business Rule Enforcement

```java
@Override
public void update(InventoryItem item) {
    String isbn = item.getBook().isbn();
    if (!inventory.containsKey(isbn)) {
        throw new IllegalArgumentException("Item with ISBN " + isbn + " not found");
    }
    inventory.put(isbn, item);
}
```

# Service Layer

The service layer serves as the business logic hub, orchestrating workflows and enforcing business rules. This layer demonstrates how dependency injection enables shared resources while maintaining the separation of concerns between data access and business operations.

## Service Layer Responsibilities

The service layer acts as an intermediary between the view layer and repositories, providing:

- **Business Logic**: Complex workflows and decision-making
- **Data Orchestration**: Coordinating multiple data operations
- **Validation**: Business rule enforcement beyond basic input validation
- **Transaction Management**: Ensuring data consistency across operations
- **Abstraction**: Hiding repository complexity from controllers

## Shared Repository Pattern

Both services inject the same `InventoryRepository`, demonstrating how Spring's dependency injection enables resource sharing while maintaining distinct responsibilities:

```java
@Service
public class CartService {
    private final InventoryRepository inventoryRepository;
    
    @Autowired
    public CartService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
}

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
}
```

**Key Benefits:**

- **Single Source of Truth**: Both services work with the same data
- **Consistency**: Changes made by one service are immediately visible to the other
- **Resource Efficiency**: No duplication of data access logic
- **Maintainability**: Repository changes affect all services uniformly

## CartService: Complex Workflow Management

The `CartService` demonstrates sophisticated business logic for shopping cart operations:

### Add to Cart Workflow

```java
public Result<Void> addToCart(String isbn, int quantity) {
    // 1. Input validation
    if (isbn == null || isbn.trim().isEmpty()) {
        return new Result<>(false, "ISBN cannot be null or empty", null);
    }
    
    // 2. Business rule validation
    InventoryItem item = inventoryRepository.getByIsbn(isbn);
    if (item == null) {
        return new Result<>(false, "Book not found with ISBN: " + isbn, null);
    }
    
    // 3. Stock availability check
    int currentCartQuantity = cart.containsKey(isbn) ? cart.get(isbn).getQuantity() : 0;
    int newTotalQuantity = currentCartQuantity + quantity;
    
    if (newTotalQuantity > item.getQuantity()) {
        return new Result<>(false, 
            String.format("Not enough stock. Available: %d, Requested: %d",
                item.getQuantity(), newTotalQuantity), null);
    }
    
    // 4. Cart management logic
    if (cart.containsKey(isbn)) {
        // Update existing cart item
        CartItem existingCartItem = cart.get(isbn);
        existingCartItem.setQuantity(newTotalQuantity);
    } else {
        // Create new cart item with proper pricing
        BigDecimal price = item.getPrice().setScale(2, RoundingMode.HALF_UP);
        CartItem newCartItem = new CartItem(item.getBook(), newTotalQuantity, price);
        cart.put(isbn, newCartItem);
    }
    
    return new Result<>(true, 
        String.format("Added %d copies of '%s' to cart", quantity, item.getBook().title()), null);
}
```

### Checkout Process

```java
public Result<String> checkout() {
    // 1. Cart validation
    if (cart.isEmpty()) {
        return new Result<>(false, "Cart is empty", null);
    }
    
    // 2. Total calculation
    Result<BigDecimal> totalResult = getTotalPrice();
    if (!totalResult.isSuccess()) {
        return new Result<>(false, totalResult.getMessage(), null);
    }
    
    // 3. Inventory updates (transaction-like behavior)
    for (Map.Entry<String, CartItem> entry : cart.entrySet()) {
        String isbn = entry.getKey();
        CartItem cartItem = entry.getValue();
        
        InventoryItem item = inventoryRepository.getByIsbn(isbn);
        item.setQuantity(item.getQuantity() - cartItem.getQuantity());
        inventoryRepository.update(item);
    }
    
    // 4. Cart cleanup
    cart.clear();
    
    return new Result<>(true, "Checkout successful!", totalResult.getData().toString());
}
```

**CartService Characteristics:**

- **Stateful**: Maintains cart state between operations
- **Complex Logic**: Multi-step workflows with validation at each stage
- **Cross-Entity Operations**: Reads inventory, manages cart, updates stock
- **Error Handling**: Comprehensive validation with descriptive messages

## InventoryService: CRUD Operations

The `InventoryService` provides streamlined inventory management:

```java
@Service
public class InventoryService {
    
    public void updateOrAddItem(InventoryItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        String isbn = item.getBook().isbn();
        InventoryItem existingItem = inventoryRepository.getByIsbn(isbn);

        if (existingItem != null) {
            // Business logic: Update existing item
            existingItem.setQuantity(item.getQuantity());
            existingItem.setPrice(item.getPrice());
            inventoryRepository.update(existingItem);
        } else {
            // Business logic: Add new item
            inventoryRepository.add(item);
        }
    }
    
    // Additional methods for complete inventory management...
}
```

**InventoryService Characteristics:**

- **Stateless**: No internal state between method calls
- **Administrative Focus**: CRUD operations for inventory management
- **Simplified Logic**: Direct mapping to repository operations with business rules
- **Upsert Pattern**: Smart handling of update-or-insert operations

## Service Layer Differentiation

### Different Perspectives on Same Data

| Aspect         | CartService                    | InventoryService               |
| -------------- | ------------------------------ | ------------------------------ |
| **Purpose**    | Customer shopping experience   | Administrative management      |
| **Data View**  | Available books for purchase   | Complete inventory catalog     |
| **Operations** | Add/remove from cart, checkout | Create/update/delete inventory |
| **State**      | Session-based cart storage     | Stateless operations           |
| **Validation** | Stock availability, cart rules | Data integrity, business rules |
| **User Type**  | Customers                      | Administrators                 |

### Workflow Complexity Comparison

**CartService Workflows:**

```java
// Complex multi-step process
addToCart() → validate input → check inventory → verify stock → update cart
checkout() → validate cart → calculate total → update inventory → clear cart
```

**InventoryService Workflows:**

```java
// Streamlined operations
updateOrAddItem() → validate input → check existence → update or create
removeItem() → validate input → delete from repository
```

## Business Logic Examples

Stock Management in CartService

```java
// Prevents overselling by checking total demand
int currentCartQuantity = cart.containsKey(isbn) ? cart.get(isbn).getQuantity() : 0;
int newTotalQuantity = currentCartQuantity + quantity;

if (newTotalQuantity > item.getQuantity()) {
    return new Result<>(false, "Not enough stock", null);
}
```

Upsert Logic in InventoryService

```java
// Intelligent update-or-add behavior
InventoryItem existingItem = inventoryRepository.getByIsbn(isbn);
if (existingItem != null) {
    existingItem.setQuantity(item.getQuantity());
    existingItem.setPrice(item.getPrice());
    inventoryRepository.update(existingItem);
} else {
    inventoryRepository.add(item);
}
```

## Error Handling Strategies

Result Pattern in CartService

```java
// Functional-style error handling with descriptive messages
return new Result<>(false, 
    String.format("Not enough stock. Available: %d, Requested: %d",
        item.getQuantity(), newTotalQuantity), null);
```

Exception-Based in InventoryService

```java
// Traditional exception handling for invalid operations
if (item == null) {
    throw new IllegalArgumentException("Item cannot be null");
}
```

## Dependency Injection Benefits Demonstrated

### Constructor Injection

Both services use constructor injection, the preferred approach:

```java
// Clear dependencies, easy to test, immutable references
public CartService(InventoryRepository inventoryRepository) {
    this.inventoryRepository = inventoryRepository;
}
```

### Service Collaboration

Services could be injected into other services for even more complex workflows. We haven't done anything this complex yet, but note it is possible!

```java
@Service
public class OrderService {
    private final CartService cartService;
    private final InventoryService inventoryService;
    
    // Orchestrate operations across multiple services
}
```

## Key Learning Points

1. **Separation of Concerns**: Services handle business logic, repositories handle data access
2. **Shared Resources**: Multiple services can inject the same repository safely
3. **Different Perspectives**: Same data accessed differently based on use case
4. **Business Rules**: Services enforce domain-specific validation and logic
5. **Workflow Orchestration**: Complex operations broken into manageable steps
6. **Error Handling**: Consistent approaches to validation and error reporting
7. **Stateful vs Stateless**: Different patterns for different requirements
8. **Dependency Injection**: Constructor injection enables loose coupling and testability

The service layer showcases how Spring's dependency injection enables clean separation between data access and business logic while allowing multiple services to collaborate effectively around shared resources.

# View Layer (Presentation Layer)

The view layer represents the presentation tier of the application, handling user interaction and display logic. This layer demonstrates Spring Boot's flexibility in supporting different interface types and showcases how dependency injection enables clean separation between user interface and business logic.

## View Layer Architecture

The bookstore application implements a console-based user interface with a clear separation of concerns:

- **View Controllers**: Orchestrate user workflows (`Kiosk`, `Inventory`). Note that these could be named `KioskController` and `InventoryController`, but again, I prefer to use the word controller in web-based MVC applications.
- **I/O Components**: Handle user input/output (`KioskIO`, `InventoryIO`)
- **Application Entry Point**: Manages application startup and mode selection (`BookstoreApplication`)

> Note that there is some duplicated logic in the IO classes. I decided the duplication was fine because they are two completely different classes of users... those rules may change over time.

## Dual-Mode Application Design

The application supports two distinct operational modes, demonstrating how Spring Boot can adapt to different user scenarios:

### Mode Selection via Configuration

```java
@SpringBootApplication
public class BookstoreApplication implements CommandLineRunner {
    
    @Value("${bookstore.mode:kiosk}")
    private String mode;
    
    @Override
    public void run(String... args) throws Exception {
        if (mode.equalsIgnoreCase("admin")) {
            inventory.run();
        } else {
            kiosk.run();
        }
    }
}
```

**Configuration Options:**

```properties
# Kiosk mode (default) - Customer interface
bookstore.mode=kiosk

# Admin mode - Inventory management interface
bookstore.mode=admin
```

**Key Benefits:**

- **Single Deployment**: One application serves multiple user types
- **Configuration-Driven**: No code changes needed to switch modes
- **Resource Sharing**: Both modes use the same services and repositories
- **Consistent Business Logic**: Same validation and rules across interfaces

## Kiosk Mode: Customer Interface

The `Kiosk` class provides a customer-facing shopping experience:

```java
@Component
public class Kiosk {
    private final CartService cartService;
    private final KioskIO kioskIO;
    
    @Autowired
    public Kiosk(CartService cartService, KioskIO kioskIO) {
        this.cartService = cartService;
        this.kioskIO = kioskIO;
    }
}
```

### Customer Workflow Features

- **Add to Cart**: Browse and select books with quantity
- **Remove from Cart**: Modify cart contents
- **View Cart**: Display current selections and total
- **Checkout**: Complete purchase transaction

### Example: Add to Cart Flow

```java
private void handleAddToCart() {
    kioskIO.displaySectionHeader("Add Book to Cart");
    
    String isbn = kioskIO.getStringInput("Enter ISBN: ");
    if (isbn == null) return; // User cancelled
    
    Integer quantity = kioskIO.getIntegerInput("Enter quantity: ");
    if (quantity == null) return; // User cancelled
    
    Result<Void> result = cartService.addToCart(isbn, quantity);
    
    if (result.isSuccess()) {
        kioskIO.displaySuccess(result.getMessage());
    } else {
        kioskIO.displayError(result.getMessage());
    }
}
```

**Kiosk Characteristics:**

- **Customer-Focused**: Simple, intuitive interface
- **Transaction-Oriented**: Guided shopping experience
- **Error-Friendly**: Clear feedback for invalid operations
- **Service Integration**: Direct use of `CartService` for business logic

## Inventory Mode: Administrative Interface

The `Inventory` class provides comprehensive inventory management:

```java
@Component
public class Inventory {
    private final InventoryService inventoryService;
    private final InventoryIO inventoryIO;
    
    @Autowired
    public Inventory(InventoryService inventoryService, InventoryIO inventoryIO) {
        this.inventoryService = inventoryService;
        this.inventoryIO = inventoryIO;
    }
}
```

### Administrative Features

- **Add/Update Items**: Create new inventory or modify existing
- **Remove Items**: Delete books from inventory with confirmation
- **View Single Item**: Detailed item information
- **View All Items**: Complete inventory listing

### Example: Smart Add/Update Operation

```java
private void handleAddOrUpdateItem() {
    // Collect all required information
    String isbn = inventoryIO.getStringInput("Enter ISBN: ");
    String title = inventoryIO.getStringInput("Enter book title: ");
    String author = inventoryIO.getStringInput("Enter author: ");
    String genre = inventoryIO.getStringInput("Enter genre: ");
    Integer quantity = inventoryIO.getIntegerInput("Enter quantity: ");
    BigDecimal price = inventoryIO.getBigDecimalInput("Enter price: $");
    
    try {
        Book book = new Book(isbn, title, author, genre);
        InventoryItem item = new InventoryItem(book, quantity, price);
        
        // Check if this is an update or add operation
        InventoryItem existingItem = inventoryService.getItem(isbn);
        
        inventoryService.updateOrAddItem(item);
        
        if (existingItem != null) {
            inventoryIO.displaySuccess("Item updated successfully!");
        } else {
            inventoryIO.displaySuccess("Item added successfully!");
        }
    } catch (Exception e) {
        inventoryIO.displayError("Failed to add/update item: " + e.getMessage());
    }
}
```

**Inventory Characteristics:**

- **Administrative Focus**: Comprehensive management capabilities
- **Data-Intensive**: Detailed input collection and validation
- **Confirmation-Based**: Safety checks for destructive operations
- **Informational**: Rich feedback and status reporting

## I/O Components: Separation of Interface Logic

Both view controllers delegate all user interaction to dedicated I/O components:

### KioskIO: Customer-Friendly Interface

```java
@Component
public class KioskIO {
    private final Scanner scanner;
    
    public void displayCartContents(List<CartItem> cartContents) {
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("                              SHOPPING CART");
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        
        // Formatted cart display with totals
    }
}
```

### InventoryIO: Administrative Interface

```java
@Component
public class InventoryIO {
    public void displayInventoryItems(List<InventoryItem> items) {
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("                              INVENTORY ITEMS");
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        
        // Detailed tabular inventory display
    }
}
```

## Input Validation and User Experience

### Robust Input Handling

```java
public Integer getIntegerInput(String prompt) {
    System.out.print(prompt);
    String input = scanner.nextLine().trim();
    
    if (input.isEmpty()) {
        displayError("Input cannot be empty. Please try again.");
        return getIntegerInput(prompt); // Recursive retry
    }
    
    try {
        int value = Integer.parseInt(input);
        if (value <= 0) {
            displayError("Please enter a positive number.");
            return getIntegerInput(prompt); // Validation retry
        }
        return value;
    } catch (NumberFormatException e) {
        displayError("Please enter a valid number.");
        return getIntegerInput(prompt); // Format retry
    }
}
```

### Confirmation Dialogs

```java
public boolean getConfirmation(String prompt) {
    System.out.print(prompt);
    String input = scanner.nextLine().trim().toLowerCase();
    
    while (!input.equals("y") && !input.equals("n") &&
           !input.equals("yes") && !input.equals("no")) {
        displayError("Please enter 'y' for yes or 'n' for no.");
        System.out.print(prompt);
        input = scanner.nextLine().trim().toLowerCase();
    }
    
    return input.equals("y") || input.equals("yes");
}
```

## Spring Boot Integration Points

### Component Registration

All view classes are Spring-managed components:

```java
@Component  // Kiosk, Inventory, KioskIO, InventoryIO
public class Kiosk { }
```

### Dependency Injection in Views

```java
// Constructor injection ensures dependencies are available
@Autowired
public Kiosk(CartService cartService, KioskIO kioskIO) {
    this.cartService = cartService;
    this.kioskIO = kioskIO;
}
```

### CommandLineRunner Implementation

```java
@SpringBootApplication
public class BookstoreApplication implements CommandLineRunner {
    // Spring Boot manages application lifecycle
    // CommandLineRunner enables console application behavior
}
```

## Design Patterns Demonstrated

### Separation of Concerns

- **View Controllers**: Workflow orchestration
- **I/O Components**: User interface and input validation
- **Services**: Business logic and data operations
- **Repositories**: Data persistence

### Strategy Pattern

The strategy pattern is a fancy name for using interfaces to change implementations. We've been doing this all along.

- Different view implementations for different user types
- Same services and business logic across different interfaces
- Configuration-driven strategy selection

## Error Handling and User Feedback

### Consistent Messaging

```java
// Success feedback
kioskIO.displaySuccess("Added 2 copies of 'The Hobbit' to cart");

// Error feedback  
kioskIO.displayError("Not enough stock. Available: 5, Requested: 10");

// Informational feedback
kioskIO.displayInfo("Cart is empty. Nothing to checkout.");
```

### Graceful Degradation

- Recursive input validation with retry
- User cancellation support (null returns)
- Exception handling with user-friendly messages
- Confirmation dialogs for destructive operations

## Key Learning Points

1. **View Layer Separation**: Clear distinction between UI logic and business logic
2. **Dependency Injection**: Services injected into view controllers
3. **Configuration-Driven Behavior**: Application mode selection via properties
4. **Component Architecture**: Modular design with focused responsibilities
5. **Input Validation**: Robust handling of user input with retry mechanisms
6. **User Experience**: Consistent messaging and confirmation patterns
7. **Spring Boot Integration**: CommandLineRunner for console applications
8. **Design Patterns**: Strategy pattern implementation

The view layer demonstrates how Spring Boot's dependency injection facilitates a clean separation between presentation logic and business operations, while supporting multiple user interfaces through configuration-driven component selection.