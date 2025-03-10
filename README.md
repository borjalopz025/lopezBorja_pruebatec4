#lopezBorja_pruebatec4

Aplicación de Reservas de Hotel y Vuelos
Este proyecto es una aplicación que permite a los usuarios realizar reservas tanto de hoteles como de vuelos, integrando ambas funcionalidades en una sola plataforma para facilitar la gestión de viajes.

Descripción General
La aplicación está compuesta por varias entidades que interactúan entre sí a través de relaciones One-to-Many
(uno a muchos). El sistema permite realizar la creación de reservas para hoteles y vuelos, 
además de gestionar las reservas existentes a través de operaciones CRUD 
(Crear, Leer, Actualizar y Eliminar). Las reservas se pueden hacer solo si hay disponibilidad, 
lo cual se verifica mediante filtraciones basadas en la información de los vuelos y hoteles.

Entidades y Relaciones
En este sistema se manejan cinco entidades principales, 
que se corresponden con las tablas de la base de datos. A continuación se describen las entidades y sus
relaciones:

Cliente: Representa a los usuarios que realizan las reservas.

Hotel: Representa los hoteles disponibles para reserva.

Vuelo: Representa los vuelos disponibles para reserva.

ReservaHotel: Representa la reserva de un hotel asociada a un cliente.

ReservaVuelo: Representa la reserva de un vuelo asociada a un cliente.

Relaciones entre Entidades
Cliente y Reservas: Existe una relación One-to-Many entre Cliente y ambas entidades de reserva 
(ReservaHotel y ReservaVuelo). Un cliente puede tener múltiples reservas, tanto de hotel como de vuelo.

Hotel y ReservaHotel: Se establece una relación One-to-Many entre Hotel y ReservaHotel.
Un hotel puede tener múltiples reservas, pero cada reserva pertenece a un solo hotel.

Vuelo y ReservaVuelo: De manera similar, se establece una relación One-to-Many entre Vuelo y ReservaVuelo.
Un vuelo puede ser reservado por varios clientes, pero cada reserva está vinculada a un solo vuelo.

Lógica de Reservas
La aplicación permite realizar reservas mediante el uso de un POST que crea una reserva a partir de las entidades padres (Hotel y Vuelo).

Filtración de Disponibilidad:
Estos métodos permiten filtrar reservas de vuelos y hoteles según criterios específicos como fechas y 
destino/origen. Ambos obtienen una lista de opciones disponibles y aplican filtros usando stream().
Verifican que el destino (o destino y origen en el caso de vuelos) coincida si se proporciona, 
y filtran según las fechas, asegurando que la fecha de inicio no sea posterior a la disponible 
y que la fecha de fin no sea anterior a la permitida. Finalmente, retornan la lista filtrada de vuelos u
hoteles que cumplen con los criterios de búsqueda.

Creación de Reservas:
Estos métodos gestionan la creación de reservas para vuelos y hoteles, 
asegurando que se cumplan ciertos criterios antes de confirmar la reserva.
Ambos validan la entrada de datos obligatorios, como fechas y ubicaciones,
y verifican la disponibilidad del servicio solicitado. Luego, calculan el precio total en función de la 
duración de la estadía o el vuelo, y actualizan el estado de la reserva. Finalmente, 
convierten los datos a una entidad para almacenarlos en la base de datos y retornan un DTO con la información actualizada de la reserva confirmada.