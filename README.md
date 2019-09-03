Code Review / Refactoring exercise

Please review the following code snippet. Assume that all referenced assemblies have been properly included. The code is used to log different messages throughout an application. We want the ability to be able to log to a text file, the console and/or the database. Messages can be marked as message, warning or error. We also want the ability to selectively be able to choose what gets logged, such as to be able to log only errors or only errors and warnings.

1.	If you were to review the following code, what feedback would you give? Please be specific and indicate any errors that would occur as well as other best practices and code refactoring that should be done. 

2.	Rewrite the code based on the feedback you provided in question 1. 


Solucion

Punto 1:

1- La variable initialized no es utilizada (línea 21).

2- Línea 25 a 35, el constructor debería de ser un método static para inicializar las variables.

3- Línea 38 a 41, messageText es validado si es nulo o vacío luego de realizar un trim(); por lo tanto el trim() puede resultar en NullPointerException.

4- Línea 49 a 55, no se realiza control de la conexión a la base de datos y de ejecución del script.

5- Línea 57 a 68 y 81 a 91; condicionales repetidos.

6- Línea 72, variable “l” no está inicializada por lo tanto no aporta nada a la concatenación realizada posteriormente.

7- Linea 73 a 76, no hace falta verificar la existencia del archivo; ya que FileHandler tiene la opción de ir concatenando los logs si el archivo existe.

8- Hay código distribuido de mala forma, por ejemplo la conexión de la base de datos, y posterior ejecución del script.

9- Los nombres de las variables son pocos descriptivos.

