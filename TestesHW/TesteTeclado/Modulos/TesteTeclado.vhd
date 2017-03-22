-------------------------------------------------------------------
-- Elementos de Sistemas
-------------------------------------------------------------------
-- Luciano Pereira
-------------------------------------------------------------------
-- Descricao :
-- Teste de Teclado
-------------------------------------------------------------------
-- Historico:
--  29/11/2016 : Criacao do projeto
-------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;
use IEEE.std_logic_unsigned.all;
use work.all;

entity TesteTeclado is

   PORT(
        -- Sistema
        CLK : IN  STD_LOGIC;
        RST : IN  STD_LOGIC;
		  LED : OUT STD_LOGIC_VECTOR(7 DOWNTO 0);

		  -- Teclado
		  key_clk      : IN  STD_LOGIC;                     --clock signal from PS/2 keyboard
		  key_data     : IN  STD_LOGIC                     --data signal from PS/2 keyboard
		  
       );
end entity;


ARCHITECTURE logic OF TesteTeclado IS

component ps2_keyboard_to_ascii IS
  --GENERIC(
  --   clk_freq                  : INTEGER := 50_000_000; --system clock frequency in Hz
  --   ps2_debounce_counter_size : INTEGER := 8);         --set such that 2^size/clk_freq = 5us (size = 8 for 50MHz)
  PORT(
      clk        : IN  STD_LOGIC;                     --system clock input
      ps2_clk    : IN  STD_LOGIC;                     --clock signal from PS2 keyboard
      ps2_data   : IN  STD_LOGIC;                     --data signal from PS2 keyboard
      ascii_new  : OUT STD_LOGIC;                     --output flag indicating new ASCII value
      ascii_code : OUT STD_LOGIC_VECTOR(6 DOWNTO 0)); --ASCII value
END component;

BEGIN

KEYBOATD_MAPED : ps2_keyboard_to_ascii PORT MAP (
			clk         => CLK, 
         ps2_clk     => key_clk,
         ps2_data    => key_data,
         ascii_code  => LED(6 downto 0),
			ascii_new   => LED(7)
	 );

END logic;


