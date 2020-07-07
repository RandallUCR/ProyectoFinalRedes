Para el uso de la aplicación es necesario implementar jdk 8 o superior, conector a base de datos mysql (incluido en los ambos proyectos).

El FTPServer incluye una conexión a base de datos para manejar el control de usarios y registrarlos, también posee una clase Server, la cual 
contiene un server socket el cual se iniciará en el puerto indicado por el usuario, al inicar este inicia un hilo el cual iniciara un hilo con 
cada socket cliente que se conecte, creando una carpeta si no la tiene o enviadole al cliente los datos de su propia carpeta.

El FTPCliente incluye también una conexion a la base de datos para realizar el proceso de login y comenzar a utilizar el servidor ftp, el cliente 
debe ingresa el puerto del servidor al que desea conectarse, una vez dentro el usuario podra enviar archivos al ServerSocket y se les mostraran estos en una tabla.

El proyecto posee un puntero hacia una carpeta indicada por el servidor, este puntero logrará mantener actualizado los archivos del servidor con los de esa carpeta
respectivamente, este proceso se lleva acabo tras ejecutar el botón drive y escoger la carpeta deseada.

Este proyecto requiere conexión a base de datos por lo cual utiliza el siguiente esquema de bases de datos y los siguientes procedimientos almacenados en MYSQL:

https://drive.google.com/file/d/1LMmQZOzEPOHa7gEt7yfES-pqoOY3z2k8/view?usp=sharing


https://drive.google.com/file/d/1q3eQutkEoAt1l-G3ugQhQBFDPBi-of42/view?usp=sharing

Los algortimos implementados son sencillos, practicos y seguros, ya que estos alteran el valor de los bytes del archivo al ser enviados, una vez llegados al destino,
se aplica la desencriptación con el patrón recibido, de esta forma podremos alterar los bytes a su manera correcta y transformarlos en un mensaje, el cliente ni el 
servidor se deben preocupar por esto ya que es automático.

El cliente si lo desea puede utilizar el botón drive para escoger una carpeta la cual se mantendrá actualizada constantemente, en caso contrario solo basta con no
utilizar dicha función y se logrará continuar sin problema alguno.



