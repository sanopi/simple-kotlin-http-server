# Simple Kotlin Http Server
This is a simple HTTP Server written in Kotlin.  
I made it for the practice of using Kotlin.
  
## What I made (referring to RFC)
- GET method
  - Status Code
    - 200
    - 400
    - 403
    - 404
    - 500
  - MIME Type
    - text
    - html
    - css
    - javascript
    - jpg/jpeg
    - png
    - gif
    - octet-stream
- Multi Thread

## Order of Implementation (represented by branch name)
1. server_socket
2. return_to_client
3. mirror_response
4. first_http_response
5. html_on_response
6. init_response
7. read_inner_files
8. img_file
9. multi_thread

## Why I made this. 
What would you do first in learning a new programming language?  

If you start with making Web Application, you would think 
"Why am I writing HTML?😇 Why do I establish SQL Server?😇".
Making Web Application needs a lot that is not the essence of programming languages.

It's good to make simple HTTP Server like this for starting learning a new programming language, I think.
