LIBRARY IEEE; 
USE IEEE.STD_LOGIC_1164.all;
  
ENTITY alu IS
   port(x,y: in STD_LOGIC_VECTOR(15 downto 0);
        zx,nx,zy,ny,f,no: in STD_LOGIC;
		  zr,ng: out STD_LOGIC;
        saida: buffer STD_LOGIC_VECTOR(15 downto 0) 
   ); 
END ALU; 
 
ARCHITECTURE strucULA OF alu IS

	COMPONENT zerador16 IS
		PORT(z   : in STD_LOGIC;
			  a   : in STD_LOGIC_VECTOR(15 downto 0);
			  y   : out STD_LOGIC_VECTOR(15 downto 0) 
			); 
	END COMPONENT;
	
	COMPONENT negador16 is
   port(z   : in STD_LOGIC;
	     a   : in STD_LOGIC_VECTOR(15 downto 0);
        y   : out STD_LOGIC_VECTOR(15 downto 0) 
		); 
	end COMPONENT;
	
	COMPONENT fa16 is
	port(a, b: in STD_LOGIC_VECTOR(15 downto 0);
		  ca: out STD_LOGIC;
		  sum: out STD_LOGIC_VECTOR(15 downto 0)
		 );
	end COMPONENT;
	
	COMPONENT and16 is
   port(a   : in STD_LOGIC_VECTOR(15 downto 0);
        b   : in STD_LOGIC_VECTOR(15 downto 0);
        y   : out STD_LOGIC_VECTOR(15 downto 0) 
		); 
	end COMPONENT; 
 
	COMPONENT comparador16 is
   port(
	     a   : in STD_LOGIC_VECTOR(15 downto 0);
        zr   : out STD_LOGIC;
		  ng   : out STD_LOGIC 
		); 
	end COMPONENT; 
	
   COMPONENT mux16 is
   port(sel : in STD_LOGIC;
	     a   : in STD_LOGIC_VECTOR(15 downto 0);
        b   : in STD_LOGIC_VECTOR(15 downto 0);
        y   : out STD_LOGIC_VECTOR(15 downto 0) 
		 ); 
   end COMPONENT;
	
   SIGNAL zxout,zyout,nxout,nyout,andout,adderout,muxout: std_logic_vector(15 downto 0); --todos os caras que fazem as conexoes entre os elementos da ALU
	--SIGNAL ca : std_logic;
	--SIGNAL saidaU7: std_logic_vector(15 downto 0);
	
begin 
	u0 : zerador16 port map (zx, x, zxout); --zerador x
	u1 : negador16 port map (nx, zxout, nxout); --negador x
	u2 : zerador16 port map (zy, y, zyout); --zerador y
	u3 : negador16 port map (ny, zyout, nyout); --negador y
	u4 : fa16 port map (nxout, nyout, open, adderout); --adder x+y
	u5 : and16 port map (nxout, nyout, andout); --and x & y
	u6 : mux16 port map (f, andout, adderout, muxout); --mux escolhe a saida do adder ou do and
	u7 : negador16 port map (no, muxout, saida); --nega a saida do mux
	u8 : comparador16 port map (saida, zr, ng);

end strucULA;