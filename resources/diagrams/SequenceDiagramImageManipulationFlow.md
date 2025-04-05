```mermaid
sequenceDiagram
    participant Frontend
    participant REST Upload Controller
    participant File Storage Service
    participant GraphQL Controller
    participant Database
    participant REST Download Controller
    participant REST Delete Controller
    participant REST Update Controller

    %% Upload Sequence
    Frontend->>REST Upload Controller: POST /images/upload/clothingItem (multipart/form-data)
    REST Upload Controller->>File Storage Service: storeFile(file, "clothing_items")
    File Storage Service-->>REST Upload Controller: fileName (String)
    REST Upload Controller-->>Frontend: { "fileName": fileName }
    Frontend->>GraphQL Controller: Mutation addClothingItem(photoUrl: fileName)
    GraphQL Controller->>Database: Save clothing item with photoUrl
    Database-->>GraphQL Controller: ClothingItem data
    GraphQL Controller-->>Frontend: ClothingItem data

    %% Download Sequence
    Frontend->>GraphQL Controller: Query clothingItem(id)
    GraphQL Controller->>Database: Retrieve clothingItem with id
    Database-->>GraphQL Controller: ClothingItem data (photoUrl)
    GraphQL Controller-->>Frontend: ClothingItem data (photoUrl)
    Frontend->>REST Download Controller: GET /images/download/clothingItem/photoUrl
    REST Download Controller->>File Storage Service: readFile(photoUrl, "clothing_items")
    File Storage Service-->>REST Download Controller: imageData (byte[])
    REST Download Controller-->>Frontend: imageData (image)

    %% Delete Sequence
    Frontend->>GraphQL Controller: Query clothingItem(id)
    GraphQL Controller->>Database: Retrieve clothingItem with id
    Database-->>GraphQL Controller: ClothingItem data (photoUrl)
    GraphQL Controller-->>Frontend: ClothingItem data (photoUrl)
    Frontend->>REST Delete Controller: DELETE /images/delete/clothingItem/photoUrl
    REST Delete Controller->>File Storage Service: deleteFile(photoUrl, "clothing_items")
    File Storage Service-->>REST Delete Controller: deleted (boolean)
    REST Delete Controller-->>Frontend: { "deleted": deleted }
    Frontend->>GraphQL Controller: Mutation deleteClothingItem(id)
    GraphQL Controller->>Database: Delete clothingItem with id
    Database-->>GraphQL Controller: Success
    GraphQL Controller-->>Frontend: Success

    %% Update Sequence
    Frontend->>REST Update Controller: PUT /images/update/clothingItem/oldPhotoUrl (multipart/form-data)
    REST Update Controller->>File Storage Service: updateFile(file, oldPhotoUrl, "clothing_items")
    File Storage Service-->>REST Update Controller: newFileName (String)
    REST Update Controller-->>Frontend: { "fileName": newFileName }
    Frontend->>GraphQL Controller: Mutation updateClothingItem(id, photoUrl: newFileName)
    GraphQL Controller->>Database: Update clothingItem with new photoUrl
    Database-->>GraphQL Controller: Updated ClothingItem data
    GraphQL Controller-->>Frontend: Updated ClothingItem data