library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;
use IEEE.std_logic_unsigned.all;
use work.all;

entity TesteDisplay is

   PORT(
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
       );
end entity;


ARCHITECTURE logic OF TesteDisplay IS

component Screen is
   PORT(
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


SIGNAL INPUT         : STD_LOGIC_VECTOR(15 downto 0) := "1111111111111111";
SIGNAL ADDRESS       : STD_LOGIC_VECTOR(13 downto 0) := "00000000000000";  -- meio 00100101101010
SIGNAL LOAD          : STD_LOGIC;
SIGNAL CLK_2KHZ     : STD_LOGIC;

BEGIN

DISPLAY: Screen  port map (
         INPUT       => INPUT,
			LOAD        => LOAD,
			ADDRESS		=> ADDRESS,
			CLK         => CLK, 
			RST         => RST, 
			LCD_CS_N 	=> LCD_CS_N , 
			LCD_D 		=> LCD_D, 
			LCD_RD_N 	=> LCD_RD_N, 
			LCD_RESET_N => LCD_RESET_N, 
			LCD_RS 		=> LCD_RS, 
			LCD_WR_N 	=> LCD_WR_N
			);	
			
--PLL_inst : PLL2 PORT MAP (
--		inclk0	 => CLK,
--		c0	 		 => CLK_2KHZ
--	);

PLL_inst : PLL PORT MAP (
		areset	 => '0',
		inclk0	 => CLK,
		c0			 => OPEN,
		c1	 		 => CLK_2KHZ,
		locked	 => OPEN
	);
	
process (CLK_2KHZ)
variable enrola : integer := 0;
begin
    if(rising_edge(CLK_2KHZ)) then
	  if(enrola > 1000) then
	    if(to_integer(unsigned( ADDRESS )) < 4800) then
	      --LOAD <= '1';
			LOAD <= not LOAD;
         INPUT <= "1111111111111111";
		   ADDRESS <= std_logic_vector(to_unsigned(to_integer(unsigned( ADDRESS )) + 1, 14));
	     else
	      LOAD <= '0';
		  end if;
		end if;
		enrola := enrola + 1;
    end if;
END PROCESS;


END logic;


