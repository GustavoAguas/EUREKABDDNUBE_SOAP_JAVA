using WS_EUREKA2.Interface;
using WS_EUREKA.EurekaService;
using WS_EUREKA.Model;
using System;
using System.Collections.Generic;

namespace WS_EUREKA2.ws
{
    public class WSEurekaBank : IWSEurekaBank
    {
        private readonly EurekaService _service = new EurekaService();

        // 🔹 LOGIN
        public bool Login(string usuario, string clave)
        {
            return _service.ValidarUsuario(usuario, clave);
        }

        // 🔹 CONSULTA DE MOVIMIENTOS
        public List<Movimiento> LeerMovimientos(string cuenta)
        {
            return _service.LeerMovimientos(cuenta);
        }

        // 🔹 TRANSACCIONES
        public int RegistrarDeposito(string cuenta, double importe, string codEmp)
        {
            return _service.RegistrarDeposito(cuenta, importe, codEmp);
        }

        public int RegistrarRetiro(string cuenta, double importe, string codEmp)
        {
            return _service.RegistrarRetiro(cuenta, importe, codEmp);
        }

        public int RegistrarTransferencia(string cuentaOrigen, string cuentaDestino, double importe, string codEmp)
        {
            return _service.RegistrarTransferencia(cuentaOrigen, cuentaDestino, importe, codEmp);
        }

    }
}
