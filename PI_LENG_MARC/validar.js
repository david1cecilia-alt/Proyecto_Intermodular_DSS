const fs = require('fs');
const libxmljs = require('libxmljs2');

// Función para validar
function validarXML(rutaXml, rutaXsd) {
    console.log(`--- Validando: ${rutaXml} ---`);
    
    try {
        // 1. Leer los archivos
        const xmlSource = fs.readFileSync(rutaXml, 'utf8');
        const xsdSource = fs.readFileSync(rutaXsd, 'utf8');

        // 2. Parsear los documentos
        const xmlDoc = libxmljs.parseXml(xmlSource);
        const xsdDoc = libxmljs.parseXml(xsdSource);

        // 3. Validar
        if (xmlDoc.validate(xsdDoc)) {
            console.log("¡Resultado: El XML es VÁLIDO según el esquema XSD!");
        } else {
            console.log("¡Resultado: El XML es INVÁLIDO!");
            console.log("Errores encontrados:");
            // Listar cada error con su mensaje y línea
            xmlDoc.validationErrors.forEach((err, index) => {
                console.log(`${index + 1}. [Línea ${err.line}] ${err.message}`);
            });
        }
    } catch (err) {
        console.error("Error crítico al procesar los archivos:", err.message);
    }
    console.log("\n");
}

// Ejecución del script para ambos casos
validarXML('datos.xml', 'esquema.xsd');
validarXML('datos_error.xml', 'esquema.xsd');