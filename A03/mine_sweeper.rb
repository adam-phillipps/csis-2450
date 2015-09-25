require 'set'
require 'asciiart'
require 'byebug'
class MineSweeper
  def initialize
    @input = ''
    @difficulty_map = { 'easy' => 0.9, 'medium' => 0.5, 'hard' => 0.2 }
    until (@input =~ /(stop|exit|quit|cease|decist|terminate|die|3|kill)+/i)
      play_game
    end
    puts 'bye bye'
  end

  def bomb_in_square?(move)
    move == '' ? false : (@board[move[0]][move[1].to_i][0].eql? 'bomb')
  end

  def create_board(size = 20, number_of_free_spaces)
    @board = Hash[@letters.map { |letter| [letter, Hash.new { ['    ', '--'] }] }]
    number_of_free_spaces.times { unique_insert(@letters.sample, rand(1..size)) }
  end

  def unique_insert(row, column)
    until @board[row][column][0].eql? '    '
      row, column = @letters.sample, rand(@letters.size)
    end
    @board[row][column] = ['bomb', '--']
  end

  def count_surrounding_bombs(move)
    count = 0
    column_set = (1..@letters.size).to_a
    [-1,0,1].each do |row_num_modifier|
      if (@letters.include? (move[0].ord + row_num_modifier).chr)
        [-1,0,1].each do |col_num_modifier|
          if column_set.include? (move[1].to_i + col_num_modifier)
            count += 1 if @board[(move[0].ord + row_num_modifier).chr][(move[1].to_i + col_num_modifier).to_i][0].eql? 'bomb'
          end
        end
      end
    end
    @board[move[0]][move[1].to_i] = ['    ', count.to_s]
  end

  def difficulty_decipher(value)
    if value.kind_of? String
      if @difficulty_map.keys.include? value
        ((@letters.size**2) * @difficulty_map[value]).floor
      else
        puts 'I\'m not sure what that number is so I\'m going to pick for you'
        ((@letters.size**2) * @difficulty_map.values.sample).floor
      end
    else
      (@letters.size**2 - value)
    end
  end

  def dramatic_loss
    lossplosion=AsciiArt.new('./explosion.jpeg')
    puts lossplosion.to_ascii_art
  end

  def dramatic_win
    winsplosion=AsciiArt.new('./victory.jpg')
    puts winsplosion.to_ascii_art
  end

  def gather_game_info
    puts 'Welcome to mine-sweeper.  I\'m sorry to say ' + "\n" +
          'but you might never leave \'cause of how awesome this is.' + "\n" +
          'how big of a square do you want the board to be?' "\n\n"
    size = gets.chomp.to_i
    if size.kind_of? String
      puts 'I\'m not sure what that number is so I\'m going to pick for you'
      (rand(26) * @difficulty_map.values.sample).floor
    end
    size = (size <= 1) ? 1 : (size - 1)
    @letters = ('a'..(97 + size).chr).to_a
    puts 'ok, pick a level of difficulty.  There\'s five levels or ' + "\n" +
          'you can choose the amount of bombs on the board.  You can ' + "\n" +
          'have from 1 bomb to board.size - 1 bombs.  Keep in mind, your ' + "\n" +
          'board will have the number you first picked squared squares.' + "\n" +
          'type stop at any time to quit.' +  "\n\n"
    difficulty = difficulty_decipher(gets.chomp)
    [@letters.size, difficulty]
  end

  def play_game
    bomb = false
    game_info = gather_game_info
    @original_moves, @moves = game_info[1], game_info[1]
    create_board(game_info[0], game_info[0])
    until @moves == 0
      show_board 1
      @moves -= 1
      break if @moves == 0
      puts 'pick a square'
      input = gets.chomp
      move = [/\D+/.match(input)[0], /\d+/.match(input)[0]]
      break if bomb_in_square? move
      count_surrounding_bombs move
      show_board 1
    end
    (@moves == 0) ? dramatic_win : dramatic_loss
    show_board 0
  end

  def show_board(version)
    puts "there are #{(@letters.size**2) - @original_moves} bombs!"
    print '   '
    (1..@letters.size).each { |num| print "#{num}".center(9) }
    puts
    @board.map do |row|
      print "#{row[0]}  "
      (1..@letters.size).each do |num|
        print "| #{@board[row[0]][num][version].center(3)} |".center(9)
      end
      puts ''
    end
    puts "\n\n\n"
  end
end

MineSweeper.new