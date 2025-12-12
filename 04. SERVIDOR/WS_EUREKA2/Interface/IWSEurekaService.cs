using System;
using System.Collections.Generic;
using System.ServiceModel;
using WS_EUREKA.Model;

namespace WS_EUREKA2.Interface
{
    [ServiceContract]
    public interface IWSEurekaBank
    {
        // 🔹 LOGIN
        [OperationContract]
        bool Login(string usuario, string clave);

        // 🔹 MOVIMIENTOS
        [OperationContract]
        List<Movimiento> LeerMovimientos(string cuenta);

        // 🔹 TRANSACCIONES
        [OperationContract]
        int RegistrarDeposito(string cuenta, double importe, string codEmp);

        [OperationContract]
        int RegistrarRetiro(string cuenta, double importe, string codEmp);

        [OperationContract]
        int RegistrarTransferencia(string cuentaOrigen, string cuentaDestino, double importe, string codEmp);
    }
}
