
# Item Data Load System

## Project Description
The Item Data Load System is a Spring Boot project that supports CRUD (Create, Read, Update, Delete) operations for item data. This system provides a set of RESTful API endpoints to manage item information, including item details, cost, quantity, packaging, dimensions, origin, shipping, company, manufacturing date, and expiry date.

## Features
- Create new items
- Read item details by ID
- Update item information
- Delete items
- Field validations
- Method-level comments
- Test cases for all CRUD operations
- Proper error handling

## API Endpoints
- **Create Item**: `POST /api/items/add` → Response: `201 Created`
- **Read Item**: `GET api/items/{id}` → Response: `200 OK`
- **Update Item**: `PUT api/items/update/{id}` → Response: `201 Created`
- **Delete Item**: `DELETE api/items/delete/{id}` → Response: `202 Accepted`


## Item Fields

- **Item Name**: String
- **Item Cost**: String
- **Item Quantity**: Number
- **Item Pack**: Y/N
- **Item Contents**: Number (Required if `Item Pack` is Y)
- **Item Dimensions**: Number
- **Item Origin Location**: String
- **Item Can Be Shipped**: Boolean
- **Item Company**: String
- **Item Manufacturing Date & Time**: Timestamp
- **Item Expiry Date**: Date

