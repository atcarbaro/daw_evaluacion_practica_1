const api = "/api/empleados";
const formulario = document.querySelector("#empleado");
const busqueda = document.querySelector("#busqueda");
const resultado = document.querySelector("#resultado");
const estadoListado = document.querySelector("#estado-listado");
const filas = document.querySelector("#filas");
const guardar = document.querySelector("#guardar");
const cancelar = document.querySelector("#cancelar");
const titulo = document.querySelector("#titulo-formulario");
let idEdicion = null;
let filtroActivo = "";
let consultaActual = 0;
let guardando = false;

function mensaje(elemento, texto, error = false) {
    elemento.textContent = texto;
    elemento.classList.toggle("error", error);
}

async function solicitar(url, opciones) {
    const respuesta = await fetch(url, opciones);
    if (respuesta.status === 404) {
        throw new Error("El empleado ya no está disponible.");
    }
    if (respuesta.status === 400) {
        throw new Error("Revisa los datos obligatorios y el formato del correo.");
    }
    if (!respuesta.ok) {
        throw new Error("Ocurrió un error al procesar la solicitud.");
    }
    return respuesta.status === 204 ? null : respuesta.json();
}

function reiniciarFormulario() {
    idEdicion = null;
    formulario.reset();
    titulo.textContent = "Registrar empleado";
    guardar.textContent = "Registrar";
    cancelar.hidden = true;
}

function editar(empleado) {
    if (guardando) return;
    idEdicion = empleado.id;
    for (const campo of ["nombre", "apellido", "email", "especialidad"]) {
        formulario.elements[campo].value = empleado[campo] ?? "";
    }
    titulo.textContent = "Editar empleado";
    guardar.textContent = "Guardar cambios";
    cancelar.hidden = false;
    mensaje(resultado, "");
    formulario.elements.nombre.focus();
}

function crearFila(empleado) {
    const fila = document.createElement("tr");
    for (const valor of [empleado.id, empleado.nombre, empleado.apellido, empleado.email, empleado.especialidad]) {
        fila.insertCell().textContent = valor ?? "—";
    }
    const acciones = fila.insertCell();
    const editarBoton = document.createElement("button");
    editarBoton.type = "button";
    editarBoton.className = "secundario";
    editarBoton.textContent = "Editar";
    editarBoton.addEventListener("click", () => editar(empleado));
    const eliminarBoton = document.createElement("button");
    eliminarBoton.type = "button";
    eliminarBoton.className = "secundario";
    eliminarBoton.textContent = "Eliminar";
    eliminarBoton.addEventListener("click", () => eliminar(empleado, eliminarBoton));
    acciones.append(editarBoton, eliminarBoton);
    return fila;
}

async function listar() {
    const numeroConsulta = ++consultaActual;
    const parametros = new URLSearchParams({ filtro: filtroActivo });
    try {
        const empleados = await solicitar(api + "?" + parametros);
        if (numeroConsulta !== consultaActual) return;
        filas.replaceChildren(...empleados.map(crearFila));
        mensaje(estadoListado, empleados.length === 0
            ? "No se encontraron empleados."
            : empleados.length + (empleados.length === 1 ? " empleado encontrado." : " empleados encontrados."));
    } catch {
        if (numeroConsulta !== consultaActual) return;
        filas.replaceChildren();
        mensaje(estadoListado, "No se pudo cargar el listado de empleados.", true);
    }
}

async function eliminar(empleado, boton) {
    if (!window.confirm("¿Eliminar a " + empleado.nombre + " " + empleado.apellido + "?")) return;
    boton.disabled = true;
    try {
        await solicitar(api + "/" + empleado.id, { method: "DELETE" });
        if (idEdicion === empleado.id && !guardando) reiniciarFormulario();
        await listar();
        mensaje(estadoListado, estadoListado.textContent + " Empleado eliminado.", estadoListado.classList.contains("error"));
    } catch (error) {
        mensaje(estadoListado, error.message, true);
    } finally {
        boton.disabled = false;
    }
}

formulario.addEventListener("submit", async (evento) => {
    evento.preventDefault();
    const datos = Object.fromEntries(new FormData(formulario));
    for (const campo in datos) {
        datos[campo] = datos[campo].trim();
    }
    if (!datos.nombre || !datos.apellido || !datos.email) {
        mensaje(resultado, "Completa el nombre, apellido y correo electrónico.", true);
        return;
    }
    const actualiza = idEdicion !== null;
    guardando = true;
    guardar.disabled = true;
    cancelar.disabled = true;
    try {
        await solicitar(actualiza ? api + "/" + idEdicion : api, {
            method: actualiza ? "PUT" : "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(datos)
        });
        reiniciarFormulario();
        mensaje(resultado, actualiza ? "Empleado actualizado." : "Empleado registrado.");
        await listar();
    } catch (error) {
        mensaje(resultado, error.message, true);
    } finally {
        guardando = false;
        guardar.disabled = false;
        cancelar.disabled = false;
    }
});

cancelar.addEventListener("click", () => {
    reiniciarFormulario();
    mensaje(resultado, "");
});
busqueda.addEventListener("submit", (evento) => {
    evento.preventDefault();
    filtroActivo = busqueda.elements.filtro.value.trim();
    listar();
});
document.querySelector("#limpiar").addEventListener("click", () => {
    busqueda.reset();
    filtroActivo = "";
    listar();
});

listar();
