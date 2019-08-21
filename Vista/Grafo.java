package Vista;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;

public class Grafo
{
    private final ArrayList <Cara> caras;
    private int width;
    private int height;
    
    public Grafo()
    {
        width = 0;
        height = 0;
        caras = new ArrayList <>();
    }
    
    public int agregarCara(int x, int y, int radio, String nombre, int modo)
    {
        Cara c = new Cara(x,y,radio,nombre,modo);
        caras.add(c);
        
        // Si la cara es muy chica para visualizarse
        if(radio < 15)
            return -1;
        
        // Si se sale del marco
        if( ((x-radio) < 0) || ((y-radio) < 0) || ((x+radio) > width) || ((y+radio) > height) )
            return -2;
        
        // Verifica si no existe overlapping entre caras
        if(caras.size() > 1)
        {
            for(Cara v: caras)
            {
                // Evitamos que la cara nueva se verifique asi misma
                if(!v.equals(c))
                {
                    if(testColision(c.getCara(),v.getCara()))
                        return -3;
                }
            }
        }
        
        return 0;
    }
    
    public boolean eliminarCara(String name)
    {
        for(Cara c : caras)
            if(c.getNombre().equals(name))
            {
                caras.remove(c);
                return true;
            }
        
        return false;
    }
    
    public String mostarCaras()
    {
        String cad = " ";
        
        for(Cara v: caras)
            cad += v.getNombre()+" , ";
        
        return cad;
    }

    public void dibujar(Graphics2D g2)
    {
        // Pintamos los caras
        for(Cara c : caras)
	    c.dibujar(g2);
    }
    
    public void limpiar(Lienzo l)
    {
        caras.clear();
        Cara.nCaras = 0;
        l.repaint();
    }
    
    public Cara getCara(Point2D p)
    {
	for(Cara v: caras)
            if(v.getCara().contains(p))
                return v;
        
	return null;
    }

    public int getIndex(Point2D p)
    {
        for(int i = 0; i < caras.size(); i++)
            if((caras.get(i)).getCara().contains(p))
                return i;
        
        return -1;
    }
    
    public int getIndex(String v)
    {
        int i;
        
        for(i = 0; i < caras.size(); i++)
            if(caras.get(i).getNombre().equals(v))
                return i;
        
        return -1;
    }
    
    public boolean testColision(Rectangle2D a, Rectangle2D b)
    {
        if((b.getMaxY() < a.getMinY()) || (b.getMinY() > a.getMaxY()))
            return false;
        
        if((b.getMaxX() < a.getMinX()) || (b.getMinX() > a.getMaxX()))
            return false;
        
        return true;
    }
    
    private boolean testColision(Shape shapeA, Shape shapeB)
    {
        Area areaA = new Area(shapeA);
        Area areaB = new Area(shapeB);
        
        return (areaA.intersects(areaB.getBounds2D()));
     }
    
    public ArrayList <Cara> getLista() { return caras; }
    
    public void setBounds(Rectangle r){ height = r.height; width = r.width; }
    public void setHeight(int height){ this.height = height; }
    public void setWidth(int width){ this.width = width; }
}