using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using WS_EUREKA.Model;
using WS_EUREKA.AccesoDB;

namespace WS_EUREKA.EurekaService
{
    public class EurekaService
    {
        private readonly AccesoDB.AccesoDB accesoDB = new AccesoDB.AccesoDB();

        // =====================================================
        //  LEER MOVIMIENTOS
        // =====================================================
        public List<Movimiento> LeerMovimientos(string cuenta)
        {
            var lista = new List<Movimiento>();

            string sql = @"
                SELECT 
                    m.chr_cuencodigo AS cuenta,
                    m.int_movinumero AS nromov,
                    m.dtt_movifecha AS fecha,
                    t.vch_tipodescripcion AS tipo,
                    t.vch_tipoaccion AS accion,
                    m.dec_moviimporte AS importe
                FROM movimiento m
                INNER JOIN tipomovimiento t 
                    ON m.chr_tipocodigo = t.chr_tipocodigo
                WHERE m.chr_cuencodigo = @cuenta";

            using (var cn = accesoDB.GetConnection())
            {
                cn.Open();
                using (SqlCommand cmd = new SqlCommand(sql, cn))
                {
                    cmd.Parameters.AddWithValue("@cuenta", cuenta);

                    using (SqlDataReader reader = cmd.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            lista.Add(new Movimiento
                            {
                                Cuenta = reader["cuenta"].ToString(),
                                NroMov = Convert.ToInt32(reader["nromov"]),
                                Fecha = Convert.ToDateTime(reader["fecha"]),
                                Tipo = reader["tipo"].ToString(),
                                Accion = reader["accion"].ToString(),
                                Importe = Convert.ToDouble(reader["importe"])
                            });
                        }
                    }
                }
            }

            return lista;
        }


        // =====================================================
        //  REGISTRAR DEPOSITO
        // =====================================================
        public int RegistrarDeposito(string cuenta, double importe, string codEmp)
        {
            using (var cn = accesoDB.GetConnection())
            {
                cn.Open();
                SqlTransaction trx = cn.BeginTransaction();

                try
                {
                    // 1️⃣ Leer cuenta
                    string sql1 = @"
                        SELECT dec_cuensaldo, int_cuencontmov 
                        FROM cuenta 
                        WHERE chr_cuencodigo=@cuenta AND vch_cuenestado='ACTIVO'";

                    double saldo;
                    int cont;

                    using (SqlCommand cmd = new SqlCommand(sql1, cn, trx))
                    {
                        cmd.Parameters.AddWithValue("@cuenta", cuenta);

                        using (SqlDataReader dr = cmd.ExecuteReader())
                        {
                            if (!dr.Read()) return -1;

                            saldo = Convert.ToDouble(dr["dec_cuensaldo"]);
                            cont = Convert.ToInt32(dr["int_cuencontmov"]);
                        }
                    }

                    // 2️⃣ Actualizar datos de cuenta
                    saldo += importe;
                    cont++;

                    string sql2 = @"
                        UPDATE cuenta 
                        SET dec_cuensaldo=@saldo, int_cuencontmov=@cont 
                        WHERE chr_cuencodigo=@cuenta";

                    using (SqlCommand cmd = new SqlCommand(sql2, cn, trx))
                    {
                        cmd.Parameters.AddWithValue("@saldo", saldo);
                        cmd.Parameters.AddWithValue("@cont", cont);
                        cmd.Parameters.AddWithValue("@cuenta", cuenta);
                        cmd.ExecuteNonQuery();
                    }

                    // 3️⃣ Registrar movimiento
                    string sql3 = @"
                        INSERT INTO movimiento
                        (chr_cuencodigo, int_movinumero, dtt_movifecha, chr_emplcodigo, chr_tipocodigo, dec_moviimporte, chr_cuenreferencia)
                        VALUES(@cuenta, @cont, GETDATE(), @codEmp, '003', @importe, NULL)";

                    using (SqlCommand cmd = new SqlCommand(sql3, cn, trx))
                    {
                        cmd.Parameters.AddWithValue("@cuenta", cuenta);
                        cmd.Parameters.AddWithValue("@cont", cont);
                        cmd.Parameters.AddWithValue("@codEmp", codEmp);
                        cmd.Parameters.AddWithValue("@importe", importe);
                        cmd.ExecuteNonQuery();
                    }

                    trx.Commit();
                    return 1;
                }
                catch
                {
                    trx.Rollback();
                    throw;
                }
            }
        }



        // =====================================================
        //  REGISTRAR RETIRO
        // =====================================================
        public int RegistrarRetiro(string cuenta, double importe, string codEmp)
        {
            using (var cn = accesoDB.GetConnection())
            {
                cn.Open();
                SqlTransaction trx = cn.BeginTransaction();

                try
                {
                    // 1️⃣ Leer cuenta
                    string sql1 = @"
                        SELECT dec_cuensaldo, int_cuencontmov 
                        FROM cuenta 
                        WHERE chr_cuencodigo=@cuenta AND vch_cuenestado='ACTIVO'";

                    double saldo;
                    int cont;

                    using (SqlCommand cmd = new SqlCommand(sql1, cn, trx))
                    {
                        cmd.Parameters.AddWithValue("@cuenta", cuenta);

                        using (SqlDataReader dr = cmd.ExecuteReader())
                        {
                            if (!dr.Read()) return -1;
                            saldo = Convert.ToDouble(dr["dec_cuensaldo"]);
                            cont = Convert.ToInt32(dr["int_cuencontmov"]);
                        }
                    }

                    // ❗ Validar fondos
                    if (saldo < importe)
                        return -2;

                    // 2️⃣ Actualizar cuenta
                    saldo -= importe;
                    cont++;

                    string sql2 = @"
                        UPDATE cuenta 
                        SET dec_cuensaldo=@saldo, int_cuencontmov=@cont 
                        WHERE chr_cuencodigo=@cuenta";

                    using (SqlCommand cmd = new SqlCommand(sql2, cn, trx))
                    {
                        cmd.Parameters.AddWithValue("@saldo", saldo);
                        cmd.Parameters.AddWithValue("@cont", cont);
                        cmd.Parameters.AddWithValue("@cuenta", cuenta);
                        cmd.ExecuteNonQuery();
                    }

                    // 3️⃣ Registrar movimiento
                    string sql3 = @"
                        INSERT INTO movimiento
                        (chr_cuencodigo, int_movinumero, dtt_movifecha, chr_emplcodigo, chr_tipocodigo, dec_moviimporte, chr_cuenreferencia)
                        VALUES(@cuenta, @cont, GETDATE(), @codEmp, '004', @importe, NULL)";

                    using (SqlCommand cmd = new SqlCommand(sql3, cn, trx))
                    {
                        cmd.Parameters.AddWithValue("@cuenta", cuenta);
                        cmd.Parameters.AddWithValue("@cont", cont);
                        cmd.Parameters.AddWithValue("@codEmp", codEmp);
                        cmd.Parameters.AddWithValue("@importe", importe);
                        cmd.ExecuteNonQuery();
                    }

                    trx.Commit();
                    return 1;
                }
                catch
                {
                    trx.Rollback();
                    throw;
                }
            }
        }



        // =====================================================
        //  REGISTRAR TRANSFERENCIA (ORIGEN + DESTINO)
        // =====================================================
        public int RegistrarTransferencia(string cuentaOrigen, string cuentaDestino, double importe, string codEmp)
        {
            using (var cn = accesoDB.GetConnection())
            {
                cn.Open();
                SqlTransaction trx = cn.BeginTransaction();

                try
                {
                    // 1️⃣ RETIRO DE ORIGEN
                    int ret = RegistrarRetiroInterno(cuentaOrigen, importe, codEmp, cn, trx);
                    if (ret < 0)
                    {
                        trx.Rollback();
                        return ret;
                    }

                    // 2️⃣ DEPÓSITO A DESTINO
                    int dep = RegistrarDepositoInterno(cuentaDestino, importe, codEmp, cn, trx);
                    if (dep < 0)
                    {
                        trx.Rollback();
                        return dep;
                    }

                    trx.Commit();
                    return 1;
                }
                catch
                {
                    trx.Rollback();
                    throw;
                }
            }
        }



        // =====================================================
        //  INTERNOS (COMPARTIDOS)
        // =====================================================

        private int RegistrarRetiroInterno(string cuenta, double importe, string codEmp, SqlConnection cn, SqlTransaction trx)
        {
            string sql1 = @"
                SELECT dec_cuensaldo, int_cuencontmov 
                FROM cuenta 
                WHERE chr_cuencodigo=@cuenta";

            double saldo;
            int cont;

            using (SqlCommand cmd = new SqlCommand(sql1, cn, trx))
            {
                cmd.Parameters.AddWithValue("@cuenta", cuenta);

                using (SqlDataReader dr = cmd.ExecuteReader())
                {
                    if (!dr.Read()) return -1;
                    saldo = Convert.ToDouble(dr["dec_cuensaldo"]);
                    cont = Convert.ToInt32(dr["int_cuencontmov"]);
                }
            }

            if (saldo < importe)
                return -2;

            // Actualizar
            saldo -= importe;
            cont++;

            string sql2 = @"
                UPDATE cuenta 
                SET dec_cuensaldo=@saldo, int_cuencontmov=@cont 
                WHERE chr_cuencodigo=@cuenta";

            using (SqlCommand cmd = new SqlCommand(sql2, cn, trx))
            {
                cmd.Parameters.AddWithValue("@saldo", saldo);
                cmd.Parameters.AddWithValue("@cont", cont);
                cmd.Parameters.AddWithValue("@cuenta", cuenta);
                cmd.ExecuteNonQuery();
            }

            // Movimiento
            string sql3 = @"
                INSERT INTO movimiento
                (chr_cuencodigo, int_movinumero, dtt_movifecha, chr_emplcodigo, chr_tipocodigo, dec_moviimporte, chr_cuenreferencia)
                VALUES(@cuenta, @cont, GETDATE(), @codEmp, '009', @importe, NULL)";

            using (SqlCommand cmd = new SqlCommand(sql3, cn, trx))
            {
                cmd.Parameters.AddWithValue("@cuenta", cuenta);
                cmd.Parameters.AddWithValue("@cont", cont);
                cmd.Parameters.AddWithValue("@codEmp", codEmp);
                cmd.Parameters.AddWithValue("@importe", importe);
                cmd.ExecuteNonQuery();
            }

            return 1;
        }


        private int RegistrarDepositoInterno(string cuenta, double importe, string codEmp, SqlConnection cn, SqlTransaction trx)
        {
            string sql1 = @"
                SELECT dec_cuensaldo, int_cuencontmov 
                FROM cuenta 
                WHERE chr_cuencodigo=@cuenta";

            double saldo;
            int cont;

            using (SqlCommand cmd = new SqlCommand(sql1, cn, trx))
            {
                cmd.Parameters.AddWithValue("@cuenta", cuenta);

                using (SqlDataReader dr = cmd.ExecuteReader())
                {
                    if (!dr.Read()) return -1;
                    saldo = Convert.ToDouble(dr["dec_cuensaldo"]);
                    cont = Convert.ToInt32(dr["int_cuencontmov"]);
                }
            }

            // Actualizar
            saldo += importe;
            cont++;

            string sql2 = @"
                UPDATE cuenta 
                SET dec_cuensaldo=@saldo, int_cuencontmov=@cont 
                WHERE chr_cuencodigo=@cuenta";

            using (SqlCommand cmd = new SqlCommand(sql2, cn, trx))
            {
                cmd.Parameters.AddWithValue("@saldo", saldo);
                cmd.Parameters.AddWithValue("@cont", cont);
                cmd.Parameters.AddWithValue("@cuenta", cuenta);
                cmd.ExecuteNonQuery();
            }

            // Movimiento
            string sql3 = @"
                INSERT INTO movimiento
                (chr_cuencodigo, int_movinumero, dtt_movifecha, chr_emplcodigo, chr_tipocodigo, dec_moviimporte, chr_cuenreferencia)
                VALUES(@cuenta, @cont, GETDATE(), @codEmp, '008', @importe, NULL)";

            using (SqlCommand cmd = new SqlCommand(sql3, cn, trx))
            {
                cmd.Parameters.AddWithValue("@cuenta", cuenta);
                cmd.Parameters.AddWithValue("@cont", cont);
                cmd.Parameters.AddWithValue("@codEmp", codEmp);
                cmd.Parameters.AddWithValue("@importe", importe);
                cmd.ExecuteNonQuery();
            }

            return 1;
        }



        // =====================================================
        //  LOGIN
        // =====================================================
        public bool ValidarUsuario(string usuario, string password)
        {
            return usuario.ToUpper() == "MONSTER" && password == "MONSTER9";
        }
    }
}
