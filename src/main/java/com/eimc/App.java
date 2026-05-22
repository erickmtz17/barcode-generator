package com.eimc;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.apache.commons.lang.RandomStringUtils;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

public class App {

    public static void main(String[] args) {

        try {
            String texto = RandomStringUtils.randomNumeric(12);
            int dpi = 150;

            // 1. Configurar el tipo de código de barras
            Code128Bean bean = new Code128Bean();
            bean.setModuleWidth(0.21); // Ajusta el ancho de las barras

            // 2. Generar el código de barras DIRECTO en memoria (BufferedImage)
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(
                    dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
            bean.generateBarcode(canvas, texto);
            canvas.finish();
            BufferedImage imagenCodigo = canvas.getBufferedImage();

            // 3. ACCIÓN 1: Guardar como archivo físico
            File archivoSalida = new File(System.getProperty("user.home") + File.separator + "Desktop", texto + ".png");
            ImageIO.write(imagenCodigo, "png", archivoSalida);
            System.out.println("Archivo guardado en: " + archivoSalida.getAbsolutePath());

            // 4. ACCIÓN 2: Mostrar en una pantalla (Interfaz Gráfica Swing)
            JFrame ventana = new JFrame("Visualizador de Código de Barras");
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setSize(400, 200);
            ventana.setLayout(new BorderLayout());

            // Colocar la imagen dentro de una etiqueta de texto
            JLabel etiquetaImagen = new JLabel(new ImageIcon(imagenCodigo));
            etiquetaImagen.setHorizontalAlignment(SwingConstants.CENTER);

            ventana.add(etiquetaImagen, BorderLayout.CENTER);
            ventana.setLocationRelativeTo(null); // Centrar la ventana
            ventana.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
