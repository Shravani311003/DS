#Client.py
import socket

import threading

import datetime

import time

from dateutil import parser



def send_time(sock):

    while True:

        now = str(datetime.datetime.now())

        sock.send(now.encode())

        print(f"[SENT] Local time: {now}")

        time.sleep(5)



def receive_time(sock):

    while True:

        sync_time = parser.parse(sock.recv(1024).decode())

        print(f"[SYNCED] Time received: {sync_time}")

        time.sleep(5)



def start_client(port=8080):

    client = socket.socket()

    client.connect(('localhost', port))



    threading.Thread(target=send_time, args=(client,), daemon=True).start()

    threading.Thread(target=receive_time, args=(client,), daemon=True).start()



    while True:

        time.sleep(1)  # Keep the main thread alive



if __name__ == '__main__':

    start_client()
#Server.py

import socket

import threading

import datetime

import time

from dateutil import parser

client_data = {}

def handle_client(conn, addr):

    while True:

        try:

            client_time = parser.parse(conn.recv(1024).decode())

            time_diff = datetime.datetime.now() - client_time

            client_data[addr] = (conn, time_diff)

            print(f"[{addr}] Sent time: {client_time}")

            time.sleep(5)

        except:

            break

def sync_clocks():

    while True:

        if client_data:

            avg_diff = sum([diff for _, diff in client_data.values()], datetime.timedelta()) / len(client_data)

            sync_time = datetime.datetime.now() + avg_diff

            for addr, (conn, _) in client_data.items():

                try:

                    conn.send(str(sync_time).encode())

                    print(f"[{addr}] Sent sync time: {sync_time}")

                except:

                    pass

        time.sleep(5)

def start_server(port=8080):

    server = socket.socket()

    server.bind(('localhost', port))

    server.listen()

    print("Server is running and waiting for clients...")

    threading.Thread(target=sync_clocks, daemon=True).start()

    while True:

        conn, addr = server.accept()

        print(f"[CONNECTED] {addr}")

        threading.Thread(target=handle_client, args=(conn, addr), daemon=True).start()

if __name__ == '__main__':

    start_server()

