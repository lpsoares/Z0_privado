library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;
use IEEE.std_logic_unsigned.all;
use work.all;

entity Screen is

   PORT(
	     --Display
		  INPUT : IN STD_LOGIC_VECTOR(15 downto 0);
		  LOAD : IN  STD_LOGIC;
		  ADDRESS : IN STD_LOGIC_VECTOR(13 downto 0);
		  --OUTPUT : OUT STD_LOGIC_VECTOR(15 downto 0);
        -- Sistema
        CLK : IN  STD_LOGIC;
        RST : IN  STD_LOGIC;    
        -- LCD EXTERNAL I/OS
        LCD_CS_N     : OUT   STD_LOGIC;	
        LCD_D        : INOUT STD_LOGIC_VECTOR(15 downto 0);
        LCD_RD_N     : OUT   STD_LOGIC;	
        LCD_RESET_N  : OUT   STD_LOGIC;	
        LCD_RS       : OUT   STD_LOGIC;	-- (DCx) 0 : reg, 1: command
        LCD_WR_N     : OUT   STD_LOGIC		
		  --LCD_ON       : OUT   STD_LOGIC	
       );
end entity;


ARCHITECTURE logic OF Screen IS

component ILI9341 is
   GENERIC(CLK_FREQ : NATURAL := 1000000);
   PORT(
        -- Sistema
        CLK : IN  STD_LOGIC;
        RST : IN  STD_LOGIC;

        -- LCD CONTROL INTERFACE
        PX_X    : IN STD_LOGIC_VECTOR(15 downto 0);
        PX_Y    : IN STD_LOGIC_VECTOR(15 downto 0);
        PX_COLOR: IN STD_LOGIC_VECTOR(15 downto 0);
        PX_EN   : IN STD_LOGIC;
        LCD_INIT_ACK : OUT STD_LOGIC;
       
        -- LCD EXTERNAL I/OS
        LCD_CS_N     : OUT   STD_LOGIC;	
        LCD_D        : INOUT STD_LOGIC_VECTOR(15 downto 0);
        LCD_RD_N     : OUT   STD_LOGIC;	
        LCD_RESET_N  : OUT   STD_LOGIC;	
        LCD_RS       : OUT   STD_LOGIC;	-- (DCx) 0 : reg, 1: command
        LCD_WR_N     : OUT   STD_LOGIC
		  
       );
end component;

component PLL IS
	PORT
	(
		areset		: IN STD_LOGIC  := '0';
		inclk0		: IN STD_LOGIC  := '0';
		c0		      : OUT STD_LOGIC ;
		c1		      : OUT STD_LOGIC ;
		locked		: OUT STD_LOGIC 
	);
END component;

--component PLL
--	PORT
--	(
--		areset		: IN STD_LOGIC  := '0';
--		inclk0		: IN STD_LOGIC  := '0';
--		c0			   : OUT STD_LOGIC ;
--		locked		: OUT STD_LOGIC 
--	);
--end component;
	
	
SIGNAL PX_X         : UNSIGNED(15 downto 0) := x"0000"; -- pixel X (0..239)
SIGNAL PX_Y         : UNSIGNED(15 downto 0) := x"0000"; -- pixel y (0..319)
SIGNAL PX_COLOR     : STD_LOGIC_VECTOR(15 downto 0) := x"0000"; 
SIGNAL PX_EN        : STD_LOGIC :='1';
SIGNAL CLK_1MHZ     : STD_LOGIC;
SIGNAL RST_INT      : STD_LOGIC;
SIGNAL LCD_INIT_ACK : STD_LOGIC;
SIGNAL FLAG 	     : STD_LOGIC := '1';
SIGNAL LOCKED       : STD_LOGIC; -- 0 not ok, 1 ok


BEGIN

--PLL_inst : PLL PORT MAP (
--		areset	 => RST_INT,
--		inclk0	 => CLK,
--		c0	 		 => CLK_1MHZ,
--		locked	 => OPEN
--);
	
PLL_inst : PLL PORT MAP (
	areset	 => RST_INT,
	inclk0	 => CLK,
	c0	 		 => CLK_1MHZ,
	c1			 => OPEN,
	locked	 => OPEN
);

LCD: ILI9341  port map (
			CLK             => CLK_1MHZ, 
			RST             => RST_INT, 
			PX_X            => STD_LOGIC_VECTOR(PX_X), 
			PX_Y            => STD_LOGIC_VECTOR(PX_Y), 
			PX_COLOR 		=> PX_COLOR, 
			PX_EN 			=> PX_EN, 
			LCD_INIT_ACK 	=> LCD_INIT_ACK, 
			LCD_CS_N 		=> LCD_CS_N , 
			LCD_D 			=> LCD_D, 
			LCD_RD_N 		=> LCD_RD_N, 
			LCD_RESET_N 	=> LCD_RESET_N, 
			LCD_RS 			=> LCD_RS, 
			LCD_WR_N 		=> LCD_WR_N);

process (CLK_1MHZ)
variable pixel : integer := 0;
begin
    if(rising_edge(clk_1mhz)) then
        IF (LCD_INIT_ACK = '1' and LOAD='1') THEN
           FLAG <= '1'; 
            IF (FLAG = '1') THEN
				
				    -- cor do pixel
				    IF( INPUT(pixel) = '1') THEN
					   PX_COLOR <= "1111111111111111"; -- Branco
				    ELSE
					   PX_COLOR <= "0000000000000000"; -- Branco
				    END IF;
					 --PX_COLOR <= "1111100000000000"; -- Vermelho
					 --PX_COLOR <= "0000011111100000"; -- Verde
					 --PX_COLOR <= "0000000000011111"; -- Azul
					 
					 -- posicao x e y
					 PX_Y <= "0000000000000000" + ((to_integer(unsigned(ADDRESS)) mod 20) * 16) + pixel;
					 PX_X <= "0000000011101111" - (to_integer(unsigned(ADDRESS)) / 20);
					 pixel := pixel + 1;
					 IF(pixel = 16) THEN
					    pixel := 0;
					 END IF;
					 PX_EN <= '1';
					 FLAG  <= '0';

            END IF;
        ELSE
            PX_EN <= '0';
        END IF;
    end if;
END PROCESS;

RST_INT <= NOT RST; -- RST_INT = 0 

END logic;
