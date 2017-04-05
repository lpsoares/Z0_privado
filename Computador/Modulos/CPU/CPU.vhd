-- Elementos de Sistemas
-- developed by Luciano Soares
-- file: CPU.vhd
-- date: 4/4/2017

library IEEE;
use IEEE.STD_LOGIC_1164.all;
--use IEEE.NUMERIC_STD.ALL;

entity CPU is
    port(
        clock:	     in  STD_LOGIC;                        -- sinal de clock para CPU
        inM:         in  STD_LOGIC_VECTOR(15 downto 0);    -- dados lidos da memória RAM
        instruction: in  STD_LOGIC_VECTOR(15 downto 0);    -- instrução (dados) vindos da memória ROM
        reset:       in  STD_LOGIC;                        -- reinicia toda a CPU (inclusive o Program Counter)
        outM:        out STD_LOGIC_VECTOR(15 downto 0);    -- dados para gravar na memória RAM
        writeM:      out STD_LOGIC;                        -- faz a memória RAM gravar dados da entrada
        addressM:    out STD_LOGIC_VECTOR(14 downto 0);    -- envia endereço para a memória RAM
        pcout:       out STD_LOGIC_VECTOR(14 downto 0)     -- endereço para ser enviado a memória ROM
  );
end entity;

architecture arch of CPU is

	component Mux16 is
		port ( 
				a:   in  STD_LOGIC_VECTOR(15 downto 0);
				b:   in  STD_LOGIC_VECTOR(15 downto 0);
				sel: in  STD_LOGIC;
				q:   out STD_LOGIC_VECTOR(15 downto 0)
		);
	end component;
  
	component ALU is
		port ( 
			x,y:   in STD_LOGIC_VECTOR(15 downto 0); 
			zx:    in STD_LOGIC;                     
			nx:    in STD_LOGIC;                     
			zy:    in STD_LOGIC;                     
			ny:    in STD_LOGIC;                     
			f:     in STD_LOGIC;                     
			no:    in STD_LOGIC;                     
			zr:    out STD_LOGIC;                    
			ng:    out STD_LOGIC;                    
			saida: out STD_LOGIC_VECTOR(15 downto 0) 
		); 
	end component;
	
	component Register16 is
		port(
			clock:   in STD_LOGIC;
			input:   in STD_LOGIC_VECTOR(15 downto 0);
			load:    in STD_LOGIC;
			output: out STD_LOGIC_VECTOR(15 downto 0)
		);
	end component;

	component PC is
	    port(
	        clock     : in  STD_LOGIC;
			increment : in  STD_LOGIC;
			load      : in  STD_LOGIC;
			reset     : in  STD_LOGIC;
	        input     : in  STD_LOGIC_VECTOR(15 downto 0);
	        output    : out STD_LOGIC_VECTOR(14 downto 0)
	    );
	end component;
	
	component ControlUnit is
    	port(
			instruction                 : in STD_LOGIC_VECTOR(15 downto 0);
			zr,ng                       : in STD_LOGIC;
			muxALUI_A                   : out STD_LOGIC;
			muxAM_ALU                   : out STD_LOGIC;
			zx, nx, zy, ny, f, no       : out STD_LOGIC;
			loadA, loadD, loadM, loadPC : out STD_LOGIC
	    );
	end component;

  signal s_muxALUI_A: STD_LOGIC;
  signal s_muxAM_ALU: STD_LOGIC;
  signal s_zx: STD_LOGIC;
  signal s_nx: STD_LOGIC;
  signal s_zy: STD_LOGIC;
  signal s_ny: STD_LOGIC;
  signal s_f: STD_LOGIC;
  signal s_no: STD_LOGIC;
  signal s_loadA: STD_LOGIC;
  signal s_loadD: STD_LOGIC;
  signal s_loadPC: STD_LOGIC;

  signal s_zr: std_logic := '0';
  signal s_ng: std_logic := '0';
  signal s_muxALUI_Aout: std_logic_vector(15 downto 0);
  signal s_muxAM_ALUout: std_logic_vector(15 downto 0);
  signal s_regAout: std_logic_vector(15 downto 0);
  signal s_regDout: std_logic_vector(15 downto 0);
  signal s_ALUout: std_logic_vector(15 downto 0);
  
  begin

	CU: ControlUnit port map ( instruction, s_zr, s_ng, s_muxALUI_A, s_muxAM_ALU, s_zx, s_nx, s_zy, s_ny, s_f, s_no, s_loadA, s_loadD, writeM, s_loadPC );

	REG_A: Register16 port map ( clock, s_muxALUI_Aout, s_loadA,  s_regAout );

	REG_D: Register16 port map ( clock, s_ALUout, s_loadD,  s_regDout );

	MUX_ALU_I_A : Mux16 port map ( s_ALUout, instruction, s_muxALUI_A, s_muxALUI_Aout );

	MUX_A_M_ALU : Mux16 port map ( s_regAout, inM, s_muxAM_ALU, s_muxAM_ALUout );

	PROG_COUNTER : PC port map ( clock, '1', s_loadPC, reset, s_regAout, pcout );

    ULA : ALU port map ( s_regDout, s_muxAM_ALUout, s_zx, s_nx, s_zy, s_ny, s_f, s_no, s_zr, s_ng, s_ALUout);	

	outM <= s_ALUout;
	
	addressM <= s_regAout(14 downto 0);
	 
end architecture;
