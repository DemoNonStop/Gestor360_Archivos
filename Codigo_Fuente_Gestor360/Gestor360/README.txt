# Gestor360

**Gestor360** es una aplicación de escritorio desarrollada en Java Swing con conexión a base de datos MySQL. Su objetivo es facilitar la gestión de clientes, proveedores, compras, ventas, stock, gastos e incidencias en pequeñas y medianas empresas.

---

## Requisitos

- Java JDK 17 o superior  
- MySQL Server  
- Eclipse IDE (recomendado)

---

## Instalación

1. Clona o descarga el proyecto.
2. Abre el proyecto en Eclipse.
3. Configura los datos de conexión en la clase `conecctionSQL.java`:
   ```java
   String url = "jdbc:mysql://localhost:3306/gestor360_db";
   String user = "root";
   String password = "root";
