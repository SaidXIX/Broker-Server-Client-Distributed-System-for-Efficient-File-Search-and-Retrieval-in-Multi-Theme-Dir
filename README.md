# Broker-Server-Client Distributed System for Efficient File Search and Retrieval in Multi-Theme

This program is a distributed system consisting of a broker, multiple servers, and clients, designed to efficiently search and retrieve files in a multi-theme directory. The program works as follows:

1. The broker processes a directory containing multiple subdirectories, where each subdirectory represents a theme (e.g. one subdirectory for sports, one for culture, one for politics).

2. The broker creates (or initializes) 'x' servers where 'x' is the number of themes, and assigns a subdirectory to each server.

3. A client connects to the broker and requests files from a single theme (Sport/Culture/Politics/...etc).

4. The broker responds with the address and port of the server that contains the requested files.

5. The client connects directly to the server using the information received from the broker (@+port).

6. The server asks the connected client how many files they can process. The client responds with 'z'.

7. The server sends 'z' files to the client along with a word to search for.

8. The client receives the files and the search word, calculates the number of occurrences of the word in the received files, and sends the result back to the server.

9. The server displays the client's response.

10. After each client response, the server displays a global status of all searched words in each file (optional).

## Architecture

The program follows a distributed system architecture, where the broker acts as a central point of control and communication between clients and servers. The servers are responsible for storing and processing files based on their assigned theme, and the clients are responsible for requesting files and searching for specific words.

## Technologies Used

The program is implemented in Java, using sockets for network communication between the broker, servers, and clients.

## IDE used

NetBeans


## Folder Structure
Folder/
├── Subfolder/
    ├── File1.txt
    ├── File2.txt
    └── File3.txt








## How to Use

1. Clone the repository to your local machine.
2. Run the broker, servers, and clients on separate terminals.
3. Follow the program prompts to request files and search for words.

## Credits

This program was developed by [Said Bouziani]

